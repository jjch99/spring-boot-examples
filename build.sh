#!/bin/bash

APP_NAME=spring-boot-examples
MODULE_API=spring-boot-example-api
MODULE_MAIN=spring-boot-example-web

PROFILE=dev
if [ "$1" != "" ]; then
    PROFILE="$1"
fi

WORK_DIR=$(cd "$(dirname "$0")"; pwd)
cd $WORK_DIR
echo "workdir is: $WORK_DIR"

HOSTNAME=$(hostname)
if [[ "$HOSTNAME" =~ "-scm-" ]]; then

    echo "build on SCM host: $HOSTNAME"
    # show environments
    env|sort

    # JAVA_HOME
    JDK=$(ls -l $BUILD_KIT_HOME/java|grep jdk*1.8|awk '{print $9}'|sed -n '1p')
    export JAVA_HOME=$BUILD_KIT_PATH/java/$JDK
    export PATH=$JAVA_HOME/bin:$PATH
    java -version

    # add mvn to PATH
    MAVEN=$(ls -l $BUILD_KIT_HOME/maven|grep apache-maven-3|awk '{print $9}'|sed -n '1p')
    export MAVEN_HOME=$BUILD_KIT_PATH/maven/$MAVEN
    export PATH=$MAVEN_HOME/bin:$PATH
    mvn -version

    echo "build profile: $PROFILE "
    mvn -U clean package -DskipTests -P$PROFILE -e || { echo "build failed"; exit 1; }
    mvn -U clean deploy  -DskipTests -P$PROFILE -e -pl ${MODULE_API} -am || { echo "deploy api failed"; exit 1; }

else

   echo "build profile: $PROFILE, skipTests"
   mvn -U clean package -DskipTests -P$PROFILE -e || { echo "build failed"; exit 1; }

fi

echo "build profile: $PROFILE OK"
rm -rf output && mkdir output

echo "something to do, eg: copy jar/war/zip/gz or scripts to output ..."

ls -l output
echo "done"
