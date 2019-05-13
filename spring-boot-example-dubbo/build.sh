#! /bin/bash

# all modules to output
MODULES="dubbo-provider dubbo-consumer"
MODULE_API=dubbo-api

PROFILE=dev
if [ "$1" != "" ]; then
    PROFILE="$1"
fi

WORK_DIR=$(cd "$(dirname "$0")"; pwd)
cd $WORK_DIR
echo "workdir is: $WORK_DIR"

HOSTNAME=$(hostname)
if [[ "$HOSTNAME" =~ "-scm-" ]]; then

    echo "build on host: $HOSTNAME"
    # show environments
    env|sort

    # JAVA_HOME
    JDK=$(ls -l $BUILD_KIT_PATH/java|grep jdk*1.8|awk '{print $9}'|sed -n '1p')
    export JAVA_HOME=$BUILD_KIT_PATH/java/$JDK
    export PATH=$JAVA_HOME/bin:$PATH
    java -version

    # add mvn to PATH
    MAVEN=$(ls -l $BUILD_KIT_PATH/maven|grep apache-maven-3|awk '{print $9}'|sed -n '1p')
    export MAVEN_HOME=$BUILD_KIT_PATH/maven/$MAVEN
    export PATH=$MAVEN_HOME/bin:$PATH
    mvn -version

    echo "build profile: $PROFILE "
    mvn -U clean package -DskipTests -P$PROFILE -e || { echo "build failed"; exit 1; }
    mvn -U clean deploy  -DskipTests -P$PROFILE -e -pl ${MODULE_API} -am || { echo "deploy api failed"; exit 1; }

else

    echo "build profile: $PROFILE "
    mvn -U clean package -DskipTests -P$PROFILE -e || { echo "build failed"; exit 1; }

fi

echo "build profile: $PROFILE  OK, copy files to output"
rm -rf output && mkdir output
for mod in $MODULES;
do
    cp -f ${mod}/target/${mod}-*.jar output/${PROFILE}-${mod}.jar
done
ls -l output
echo "done"
