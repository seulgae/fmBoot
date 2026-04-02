# Deployment

## Runtime

- Reverse proxy: `nginx`
- App container: `fm-app`
- DB container: `oracle-xe`
- App URL: `http://192.168.219.105/`
- Oracle URL: `jdbc:oracle:thin:@192.168.219.105:1521/XEPDB1`

## Server paths

- Deploy JAR: `/home/seulgae/fmBoot-deploy/fm-0.0.1-SNAPSHOT.jar`
- App config: `/home/seulgae/fmBoot-runtime/application.properties`

## CI/CD strategy

The repository uses a split pipeline:

1. `build` job on `ubuntu-latest`
2. `deploy` job on a `self-hosted` Linux runner installed on the deployment target

This is the preferred layout for:

- Hyper-V local VM deployment
- future AWS EC2 deployment

The deploy workflow does not require inbound SSH from GitHub-hosted runners. The target runner pulls work from GitHub and deploys locally.

## Required GitHub Actions secrets

- `SUDO_PASSWORD`

## Self-hosted runner requirements

- OS: Linux
- Access to `podman`
- Access to `nginx`
- Permission to use `sudo`
- Runner labels including `self-hosted` and `linux`

## AWS migration path

When moving to AWS later:

1. provision an EC2 instance
2. install `podman`, `nginx`, and the GitHub self-hosted runner
3. copy the same runtime config structure
4. reuse the same deploy workflow

Only these values typically need to change:

- `DEPLOY_ROOT`
- `APP_CONFIG_PATH`
- public DNS / reverse proxy settings

## Notes

- The current workflow builds with `-DskipTests`.
- The application runs on Spring Boot's embedded Tomcat behind `nginx`.
