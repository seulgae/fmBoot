# Deployment

## Runtime

- Reverse proxy: `nginx`
- App container: `fm-app`
- DB container: `oracle-xe`
- App URL: `http://192.168.219.105/`
- Oracle URL: `jdbc:oracle:thin:@192.168.219.105:1521/XEPDB1`

## Server paths

- Build/runtime JAR: `/home/seulgae/fmBoot-deploy/fm-0.0.1-SNAPSHOT.jar`
- App config: `/home/seulgae/fmBoot-runtime/application.properties`

## GitHub Actions secrets

Set these repository secrets before enabling CD:

- `SERVER_HOST`
- `SERVER_USER`
- `SERVER_SSH_KEY`
- `SERVER_SUDO_PASSWORD`

## Notes

- The current workflow builds with `-DskipTests` because the project test setup depends on external runtime services.
- The application runs on Spring Boot's embedded Tomcat behind `nginx`. There is no separate external Tomcat installation.
