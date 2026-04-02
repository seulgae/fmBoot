param(
    [string]$RemoteUser = "seulgae",
    [string]$RemoteHost = "192.168.219.105",
    [string]$RemoteAppDir = "/home/seulgae/fmBoot",
    [string]$JarPath = ".\target\fm-0.0.1-SNAPSHOT.jar",
    [string]$JavaHome = "",
    [string]$OracleHost = "localhost",
    [int]$OraclePort = 1521,
    [string]$OracleService = "XEPDB1",
    [string]$OracleAppUser = "fm",
    [string]$OracleAppPassword = "oracle",
    [string]$AppPort = "8085",
    [string]$MailUsername = "fmtest36@gmail.com"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function ConvertFrom-SecureStringPlain {
    param([Security.SecureString]$SecureValue)

    return [System.Net.NetworkCredential]::new("", $SecureValue).Password
}

function Escape-BashSingleQuoted {
    param([string]$Value)

    $replacement = [string]::Concat("'", '"', "'", '"', "'")
    return "'" + $Value.Replace("'", $replacement) + "'"
}

function Escape-SystemdEnvironmentValue {
    param([string]$Value)

    return $Value.Replace('\', '\\').Replace('"', '\"')
}

if (-not (Test-Path $JarPath)) {
    throw "JAR file not found: $JarPath"
}

$oracleSysPasswordSecure = Read-Host "Oracle SYS password" -AsSecureString
$mailPasswordSecure = Read-Host "spring.mail.password" -AsSecureString

$oracleSysPassword = ConvertFrom-SecureStringPlain $oracleSysPasswordSecure
$mailPassword = ConvertFrom-SecureStringPlain $mailPasswordSecure

$remote = "$RemoteUser@$RemoteHost"
$serviceName = "fmboot"
$remoteJarPath = "$RemoteAppDir/app.jar"
$remoteConfigPath = "$RemoteAppDir/config/application.properties"
$remoteServicePath = "/etc/systemd/system/$serviceName.service"

$remoteScript = @"
set -euo pipefail

REMOTE_APP_DIR=$(printf "%s" $(Escape-BashSingleQuoted $RemoteAppDir))
REMOTE_CONFIG_DIR="${REMOTE_APP_DIR}/config"
REMOTE_BIN_DIR="${REMOTE_APP_DIR}/bin"
REMOTE_LOG_DIR="${REMOTE_APP_DIR}/logs"
REMOTE_UPLOAD_DIR="${REMOTE_APP_DIR}/upload"
REMOTE_JAR_PATH="${REMOTE_APP_DIR}/app.jar"
REMOTE_CONFIG_PATH="${REMOTE_CONFIG_DIR}/application.properties"
SERVICE_NAME=$(printf "%s" $(Escape-BashSingleQuoted $serviceName))
SERVICE_PATH=$(printf "%s" $(Escape-BashSingleQuoted $remoteServicePath))
APP_USER=$(printf "%s" $(Escape-BashSingleQuoted $RemoteUser))
JAVA_HOME_VALUE=$(printf "%s" $(Escape-BashSingleQuoted $JavaHome))
ORACLE_SYS_PASSWORD=$(printf "%s" $(Escape-BashSingleQuoted $oracleSysPassword))
ORACLE_HOST=$(printf "%s" $(Escape-BashSingleQuoted $OracleHost))
ORACLE_PORT=$(printf "%s" $(Escape-BashSingleQuoted ([string]$OraclePort)))
ORACLE_SERVICE=$(printf "%s" $(Escape-BashSingleQuoted $OracleService))
ORACLE_APP_USER=$(printf "%s" $(Escape-BashSingleQuoted $OracleAppUser))
ORACLE_APP_PASSWORD=$(printf "%s" $(Escape-BashSingleQuoted $OracleAppPassword))
APP_PORT=$(printf "%s" $(Escape-BashSingleQuoted $AppPort))
MAIL_USERNAME=$(printf "%s" $(Escape-BashSingleQuoted $MailUsername))
MAIL_PASSWORD=$(printf "%s" $(Escape-BashSingleQuoted $mailPassword))

mkdir -p "${REMOTE_CONFIG_DIR}" "${REMOTE_BIN_DIR}" "${REMOTE_LOG_DIR}" "${REMOTE_UPLOAD_DIR}"

cat > "${REMOTE_CONFIG_PATH}" <<EOF
server.port=${APP_PORT}

spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@${ORACLE_HOST}:${ORACLE_PORT}/${ORACLE_SERVICE}
spring.datasource.username=${ORACLE_APP_USER}
spring.datasource.password=${ORACLE_APP_PASSWORD}

mybatis.type-aliases-package=com.myBatis.myService.model
mybatis.mapper-locations=mapper/*.xml
spring.sql.init.mode=always

spring.thymeleaf.prefix=classpath:templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false

spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-request-size=500MB
spring.servlet.multipart.max-file-size=500MB

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
EOF

cat > "${REMOTE_BIN_DIR}/run.sh" <<EOF
#!/bin/bash
set -euo pipefail
EOF

if [ -n "${JAVA_HOME_VALUE}" ]; then
cat >> "${REMOTE_BIN_DIR}/run.sh" <<EOF
export JAVA_HOME="${JAVA_HOME_VALUE}"
export PATH="\${JAVA_HOME}/bin:\${PATH}"
EOF
fi

cat >> "${REMOTE_BIN_DIR}/run.sh" <<'EOF'
cd "$(dirname "$0")/.."
exec java -jar app.jar --spring.config.location=file:./config/application.properties
EOF

chmod +x "${REMOTE_BIN_DIR}/run.sh"

sqlplus -s /nolog <<EOF
WHENEVER SQLERROR EXIT SQL.SQLCODE
CONNECT sys/"${ORACLE_SYS_PASSWORD}"@${ORACLE_HOST}:${ORACLE_PORT}/${ORACLE_SERVICE} AS SYSDBA
ALTER SESSION SET CONTAINER = ${ORACLE_SERVICE};
DECLARE
    v_count NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_count FROM dba_users WHERE username = UPPER('${OracleAppUser}');
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE USER ${OracleAppUser} IDENTIFIED BY "${OracleAppPassword}"';
    END IF;
END;
/
GRANT CONNECT, RESOURCE TO ${OracleAppUser};
GRANT CREATE SESSION, CREATE TABLE, CREATE VIEW, CREATE SEQUENCE, CREATE TRIGGER TO ${OracleAppUser};
ALTER USER ${OracleAppUser} QUOTA UNLIMITED ON USERS;
EXIT
EOF

sudo tee "${SERVICE_PATH}" > /dev/null <<EOF
[Unit]
Description=FM Boot Application
After=network.target

[Service]
Type=simple
User=${RemoteUser}
WorkingDirectory=${RemoteAppDir}
ExecStart=${RemoteAppDir}/bin/run.sh
SuccessExitStatus=143
Restart=always
RestartSec=5
Environment=SPRING_CONFIG_LOCATION=file:${remoteConfigPath}
Environment=JAVA_HOME=$(Escape-SystemdEnvironmentValue $JavaHome)

[Install]
WantedBy=multi-user.target
EOF

sudo systemctl daemon-reload
sudo systemctl enable "${SERVICE_NAME}"
sudo systemctl restart "${SERVICE_NAME}"
sudo systemctl --no-pager --full status "${SERVICE_NAME}" || true
"@

Write-Host "1. Upload JAR"
scp $JarPath "${remote}:${remoteJarPath}"

Write-Host "2. Apply remote configuration"
$remoteScript | ssh $remote "bash -s"

Write-Host "3. Done"
Write-Host "Check service status: ssh $remote 'sudo systemctl status $serviceName'"
