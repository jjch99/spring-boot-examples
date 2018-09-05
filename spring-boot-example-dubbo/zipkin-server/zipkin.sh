#!/usr/bin/env bash

BASEDIR=$(cd "$(dirname "$0")"; pwd)
cd $BASEDIR

source zipkin.conf

find_pid()
{
  pid=$(ps aux|grep $(pwd)/zipkin.jar | grep -v grep | awk '{print $2}')
  echo $pid
}

JAVA_VER=$(java -version 2>&1 | sed -n ';s/.* version "\(.*\)\.\(.*\)\..*"/\1\2/p;')
if [[ "$JAVA_VER" -lt 18 ]] ; then
  echo "The Java version must be 1.8 or later!";
  exit;
fi

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
        # ps -ef | grep zipkin | grep -v grep | awk '{print $2}' | xargs kill -9 > /dev/null 2>&1
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
