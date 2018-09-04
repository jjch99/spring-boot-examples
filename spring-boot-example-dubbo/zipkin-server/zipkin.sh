#!/usr/bin/env bash

BASEDIR=$(cd "$(dirname "$0")"; pwd)
cd $BASEDIR

find_pid()
{
  pid=$(ps aux|grep $(pwd)/zipkin.jar | grep -v grep | awk '{print $2}')
  echo $pid
}

case "$1" in

    "status")
        pid=$(find_pid)
        if [ "$pid" != "" ]; then
          ps -f -p $pid | cat
        else
          echo "zipkin not running"
        fi
        exit;;

    "start")
        pid=$(find_pid)
        if [ "$pid" != "" ]; then
          echo "zipkin in running"
          ps -f -p $pid | cat
        else
          nohup sh zipkin.jar start >/dev/null 2>&1 &
          sleep 3
          ps -ef | grep $(pwd)/zipkin.jar | grep -v grep
        fi
        exit;;

    "stop")
        pid=$(find_pid)
        if [ "$pid" != "" ]; then
          echo "find zipkin pid: $pid , kill it."
          kill $pid
        else
          echo "zipkin not running"
        fi
        exit;;

    *)
        echo "Usage: $0 {start|stop|status}"
        exit 1 ;;
esac
