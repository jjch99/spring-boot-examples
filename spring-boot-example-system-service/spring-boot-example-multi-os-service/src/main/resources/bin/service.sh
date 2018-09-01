#!/bin/sh

BINDIR=$(cd "$(dirname "$0")"; pwd)
cd $BINDIR

JARFILE=@bootstrap-jar@
echo JARFILE=$BINDIR/$JARFILE

BASEDIR=$(dirname "$BINDIR")
cd $BASEDIR
echo BASEDIR=$BASEDIR

JAVA_HEAP_OPTS="-Xms256m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$BASEDIR/logs"
JAVA_GC_OPTS="-verbose:gc -Xloggc:$BASEDIR/logs/gc.log -XX:+PrintGCDetails"
JAVA_OPTS="-server $JAVA_HEAP_OPTS $JAVA_GC_OPTS"
FILE_ENCODING=UTF-8

ENV=@environment@

if [ ! -x "$JAVA_HOME"/bin/java ]; then
    _RUNJAVA=java
else
    _RUNJAVA="$JAVA_HOME"/bin/java
fi

_ECHO=/bin/echo

GREP_PATTERN="${BASEDIR}/config"
function checkproc()
{
  count=`ps -ef | grep java | grep "${GREP_PATTERN}" | grep -v grep | wc -l`;
  return ${count};
}

case "$1" in

    "status")
        checkproc;
        proccount=$?
        if [ ${proccount} -gt 0 ]; then
            ps -ef | grep java | grep "${GREP_PATTERN}"
            echo ".................................................."
            echo "@project.artifactId@ is running !"
        else
            echo "@project.artifactId@ not running."
        fi
        exit;;
		
    "start")
        checkproc;
        proccount=$?
        if [ ${proccount} -gt 0 ]; then
            ps -ef | grep java | grep "${GREP_PATTERN}"
            echo ".................................................."
            echo "@project.artifactId@ is running !"
        else
            $_ECHO -n "start @project.artifactId@ "
            nohup $_RUNJAVA $JAVA_OPTS \
                -cp $BASEDIR/config:$BASEDIR \
                -Dfile.encoding=$FILE_ENCODING \
                -Dbasedir=$BASEDIR \
                -Dloader.path=$BASEDIR/lib \
                -jar $BINDIR/$JARFILE \
                    --spring.profiles.active=$ENV \
                > "$BASEDIR/logs/stdout.log" 2>&1 &

            loopcount=0
            while [ ${loopcount} -lt 15 ]
            do
                $_ECHO -n "."
                loopcount=`expr ${loopcount} + 1`
                sleep 1s
            done
            loopcount=0
            while [ ${loopcount} -lt 60 ] && [ ${proccount} -eq 0 ]
            do
                $_ECHO -n "."
                loopcount=`expr ${loopcount} + 1`
                sleep 1s
                checkproc;
                proccount=$?
            done
            echo ""
            checkproc;
            proccount=$?
            if [ ${proccount} -gt 0 ]; then
                ps -ef | grep java | grep "${GREP_PATTERN}"
                echo ".................................................."
                echo "start @project.artifactId@ ok."
            else
                echo "start @project.artifactId@ failed !"
            fi
        fi
        exit;;

    "stop")
	    checkproc;
        proccount=$?
        if [ ${proccount} -gt 0 ]; then
            $_ECHO -n "shutdown @project.artifactId@ "
            $_RUNJAVA -cp $BASEDIR/config:$BASEDIR/lib/* @shutdown-class@
            checkproc;
            proccount=$?
            loopcount=0
            while [ ${proccount} -gt 0 ]
            do
                if [ ${loopcount} -gt 60 ]; then
                    echo "shutdown @project.artifactId@ failed !"
                    exit 1;
                fi
                loopcount=`expr ${loopcount} + 1`
                $_ECHO -n "."
                sleep 1s
                checkproc;
                proccount=$?
            done
            echo ""
            echo "shutdown @project.artifactId@ ok."
        else
            echo "@project.artifactId@ not running."
            exit
        fi
        exit;;

    *)
        echo "Usage: $0 {start|stop|status}"
        exit 1 ;;
esac
