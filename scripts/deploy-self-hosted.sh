#!/usr/bin/env bash
set -euo pipefail

ARTIFACT_PATH="${ARTIFACT_PATH:?ARTIFACT_PATH is required}"
DEPLOY_ROOT="${DEPLOY_ROOT:-/home/seulgae/fmBoot-deploy}"
APP_CONFIG_PATH="${APP_CONFIG_PATH:-/home/seulgae/fmBoot-runtime/application.properties}"
APP_CONTAINER_NAME="${APP_CONTAINER_NAME:-fm-app}"
CONTAINER_IMAGE="${CONTAINER_IMAGE:-docker.io/library/eclipse-temurin:17-jre}"
APP_PORT="${APP_PORT:-8080}"
JAR_NAME="${JAR_NAME:-fm-0.0.1-SNAPSHOT.jar}"
PODMAN_BIN="${PODMAN_BIN:-podman}"

run_as_root() {
  if [[ -n "${SUDO_PASSWORD:-}" ]]; then
    printf '%s\n' "${SUDO_PASSWORD}" | sudo -S "$@"
  else
    sudo "$@"
  fi
}

if [[ ! -f "${ARTIFACT_PATH}" ]]; then
  echo "artifact not found: ${ARTIFACT_PATH}" >&2
  exit 1
fi

run_as_root mkdir -p "${DEPLOY_ROOT}"
run_as_root cp "${ARTIFACT_PATH}" "${DEPLOY_ROOT}/${JAR_NAME}"
run_as_root chown "$(id -un):$(id -gn)" "${DEPLOY_ROOT}/${JAR_NAME}"

run_as_root "${PODMAN_BIN}" pull "${CONTAINER_IMAGE}"
run_as_root "${PODMAN_BIN}" rm -f "${APP_CONTAINER_NAME}" || true
run_as_root "${PODMAN_BIN}" run -d \
  --name "${APP_CONTAINER_NAME}" \
  --restart=always \
  -p "${APP_PORT}:${APP_PORT}" \
  -v "${DEPLOY_ROOT}/${JAR_NAME}:/app/app.jar:ro,Z" \
  -v "${APP_CONFIG_PATH}:/app/config/application.properties:ro,Z" \
  "${CONTAINER_IMAGE}" \
  java -jar /app/app.jar --spring.config.location=file:/app/config/application.properties

sleep 15

run_as_root "${PODMAN_BIN}" ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
curl -I -s "http://127.0.0.1:${APP_PORT}/" | head -n 10
