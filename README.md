# FM Manager

A Spring Boot web application for futsal team management, match records, place reservation, payment, notice, and blog features.

## Overview

- Project type: Spring Boot server-side rendered web app
- Rendering: Thymeleaf
- Persistence: MyBatis + Oracle
- Security: Spring Security
- Runtime: Spring Boot embedded Tomcat
- Public URL: `http://192.168.219.105/`

## Tech Stack

- Java 11 source level
- Spring Boot 2.7.5
- Thymeleaf
- MyBatis Spring Boot Starter 2.2.2
- Oracle JDBC (`ojdbc11`)
- Spring Security
- Spring Mail
- Iamport REST client

## Features

- Team management
- Match record management
- Place list and reservation
- Payment flow
- Notice board
- Blog/community board
- Member login and mypage

## Local Development

### Requirements

- JDK 11 or newer
- Oracle DB with `XEPDB1`
- Maven Wrapper

### Default application properties

Current defaults in [application.properties](/C:/Dev/WorkSpace/fmBoot/src/main/resources/application.properties):

- App port: `8085`
- JDBC URL: `jdbc:oracle:thin:@localhost:1521/XEPDB1`
- DB username: `fm`
- Thymeleaf cache: disabled
- Multipart upload limit: `500MB`

### Run locally

```bash
./mvnw spring-boot:run
```

Or:

```bash
./mvnw -DskipTests package
java -jar target/fm-0.0.1-SNAPSHOT.jar
```

## Current Server Runtime

### Topology

- Reverse proxy: `nginx`
- Application container: `fm-app`
- Database container: `oracle-xe`
- Container runtime: `podman`
- App internal port: `8085`
- Public HTTP port: `80`
- Oracle port: `1521`

### Endpoints

- App: `http://192.168.219.105/`
- Oracle: `jdbc:oracle:thin:@192.168.219.105:1521/XEPDB1`

### Server paths

- Build workspace: `/home/seulgae/fmBoot-build`
- Deploy JAR: `/home/seulgae/fmBoot-deploy/fm-0.0.1-SNAPSHOT.jar`
- Runtime config: `/home/seulgae/fmBoot-runtime/application.properties`

## Operations

### Check containers

```bash
sudo podman ps
```

### Check logs

```bash
sudo podman logs oracle-xe | tail -n 50
sudo podman logs fm-app | tail -n 50
```

### Check nginx

```bash
sudo systemctl status nginx
curl -I http://127.0.0.1/
```

## CI/CD

GitHub Actions workflows:

- [ci.yml](/C:/Dev/WorkSpace/fmBoot/.github/workflows/ci.yml)
- [deploy.yml](/C:/Dev/WorkSpace/fmBoot/.github/workflows/deploy.yml)

### Deployment model

- `CI` runs on GitHub-hosted runner
- `Deploy` runs on a Linux `self-hosted` runner installed on the deployment target

This is the recommended model for:

- local Hyper-V VM deployment
- future AWS EC2 deployment

### Required GitHub Actions secrets

- `SUDO_PASSWORD`

### Deploy flow

1. GitHub Actions builds `target/fm-0.0.1-SNAPSHOT.jar`
2. The workflow stores the JAR as an artifact
3. The self-hosted runner downloads the artifact locally
4. The runner recreates the `fm-app` container with the new JAR
5. `nginx` proxies `80 -> 8085`

### Future AWS migration

The same deployment flow can be reused on AWS by installing the self-hosted runner on an EC2 instance and keeping the same container/runtime layout.

## Notes

- The application uses Spring Boot embedded Tomcat. There is no separate external Tomcat installation.
- CI/CD currently builds with `-DskipTests`.
- Review unrelated working tree changes before committing.

## References

- Presentation: [PPT.pdf](/C:/Dev/WorkSpace/fmBoot/PPT.pdf)
- Video: `https://youtu.be/Cb8BTgsmOXY`
