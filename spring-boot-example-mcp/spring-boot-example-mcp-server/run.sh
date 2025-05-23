#!/usr/bin/env bash

WORK_DIR=$(cd "$(dirname "$0")"; pwd)
cd $WORK_DIR
echo "workdir is: $WORK_DIR"

ROOT_DIR=$(cd "$(dirname $(dirname "$WORK_DIR"))"; pwd)
echo "rootdir is: $ROOT_DIR"

. $ROOT_DIR/setenv.sh

[ -s "${WORK_DIR}/.env" ] && source "${WORK_DIR}/.env"

$ROOT_DIR/mvnw spring-boot:run -e -s $ROOT_DIR/.mvn/settings.xml
