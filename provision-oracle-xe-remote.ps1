param(
    [string]$RemoteUser = "seulgae",
    [string]$RemoteHost = "192.168.219.105",
    [string]$Password = "rlfxogud1@",
    [string]$OracleSysPassword = "Oracle123!",
    [string]$AppUser = "fm",
    [string]$AppPassword = "oracle"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

Import-Module Posh-SSH

$secure = ConvertTo-SecureString $Password -AsPlainText -Force
$cred = New-Object System.Management.Automation.PSCredential($RemoteUser, $secure)

$session = New-SSHSession -ComputerName $RemoteHost -Credential $cred -AcceptKey

try {
    $command = @"
bash -lc '
set -e
printf "$Password\n" | sudo -S podman pull docker.io/gvenzl/oracle-xe:21-slim-faststart
printf "$Password\n" | sudo -S podman rm -f oracle-xe || true
printf "$Password\n" | sudo -S podman volume rm -f oracle-xe-data || true
printf "$Password\n" | sudo -S podman volume create oracle-xe-data
printf "$Password\n" | sudo -S podman create \
  --name oracle-xe \
  --shm-size=1g \
  --memory=2g \
  --memory-swap=3g \
  -p 1521:1521 \
  -e ORACLE_PASSWORD="$OracleSysPassword" \
  -e APP_USER="$AppUser" \
  -e APP_USER_PASSWORD="$OracleSysPassword" \
  -e INIT_SGA_SIZE="512" \
  -e INIT_PGA_SIZE="256" \
  -v oracle-xe-data:/opt/oracle/oradata \
  docker.io/gvenzl/oracle-xe:21-slim-faststart
printf "$Password\n" | sudo -S podman start oracle-xe
sleep 20
printf "$Password\n" | sudo -S podman logs oracle-xe | tail -n 120
'
"@

    Invoke-SSHCommand -SessionId $session.SessionId -Command $command -TimeOut 1800 |
        Select-Object -ExpandProperty Output
}
finally {
    if ($session) {
        Remove-SSHSession -SessionId $session.SessionId | Out-Null
    }
}
