#!/usr/bin/env bash

WORK_DIR=$(cd "$(dirname "$0")"; pwd)
cd $WORK_DIR
echo "workdir is: $WORK_DIR"

ROOT_DIR=$(cd "$(dirname "$WORK_DIR")"; pwd)
echo "rootdir is: $ROOT_DIR"

source $ROOT_DIR/setenv.sh
alias mvn=$ROOT_DIR/mvnw

mvn spring-boot:run -e -s $ROOT_DIR/.mvn/settings.xml
