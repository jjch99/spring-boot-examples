#!/usr/bin/env bash

CURR_DIR=$(cd "$(dirname "$0")"; pwd)
cd $CURR_DIR
echo "current dir is: $CURR_DIR"

PARENT_DIR=$(cd "$(dirname "$CURR_DIR")"; pwd)
cd $PARENT_DIR
echo "parent dir is: $PARENT_DIR"

ROOT_DIR=$(cd "$(dirname "$PARENT_DIR")"; pwd)
cd $ROOT_DIR
echo "root dir is: $ROOT_DIR"

. $ROOT_DIR/setenv.sh

$ROOT_DIR/mvnw clean
$ROOT_DIR/mvnw -U -e -DskipTests install -pl spring-boot-example-thrift/thrift-common -am -s $ROOT_DIR/.mvn/settings.xml
$ROOT_DIR/mvnw -U -e -DskipTests spring-boot:run -pl spring-boot-example-thrift/thrift-server -s $ROOT_DIR/.mvn/settings.xml
