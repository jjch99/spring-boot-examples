#!/bin/bash

BASEDIR=$(cd "$(dirname "$0")"; pwd)
cd $BASEDIR

source $BASEDIR/service.conf

if [[ "$PID_FOLDER" != "" && ! -d "$PID_FOLDER" ]]; then
    mkdir -p $PID_FOLDER
fi
if [[ "$LOG_FOLDER" != "" && ! -d "$LOG_FOLDER" ]]; then
    mkdir -p $LOG_FOLDER
fi

case "$1" in

    "status")
        ./${APP_NAME}.jar status
        exit;;

    "start")
        ./${APP_NAME}.jar start
        ps -ef|grep $BASEDIR/${APP_NAME}|grep -v grep
        exit;;

    "restart")
        ./${APP_NAME}.jar restart
        ps -ef|grep $BASEDIR/${APP_NAME}|grep -v grep
        exit;;

    "stop")
        ./${APP_NAME}.jar stop
        exit;;

    *)
        echo "Usage: $0 {start|stop|status|restart}"
        exit 1 ;;
esac
