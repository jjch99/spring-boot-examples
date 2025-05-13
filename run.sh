#!/usr/bin/env bash

WORK_DIR=$(cd "$(dirname "$0")"; pwd)
cd $WORK_DIR
echo "workdir is: $WORK_DIR"

. ${WORK_DIR}/setenv.sh
alias mvn=$WORK_DIR/mvnw

set -e

# Function to find Spring Boot modules recursively
find_boot_modules() {
  find . -type f -name pom.xml | while read -r pom; do
    if grep -q "spring-boot-maven-plugin" "$pom"; then
      module_path=$(dirname "$pom")
      module_name="${module_path#./}"
      echo "$module_name"
    fi
  done
}

# No argument — list all available boot modules
if [ -z "$1" ]; then
  echo "Error: Please specify the submodule name to run."
  echo "Available Spring Boot submodules:"
  find_boot_modules | sort | sed 's/^/  - /'
  echo ""
  echo "Usage: $0 <submodule-name>"
  echo "Example: $0 services/app-server"
  exit 1
fi

MODULE_NAME="$1"
MODULE_PATH="./$MODULE_NAME"
POM_FILE="$MODULE_PATH/pom.xml"

# Check if module directory exists
if [ ! -d "$MODULE_PATH" ]; then
  echo "Error: Module directory '$MODULE_PATH' does not exist."
  exit 1
fi

# Check if pom.xml exists
if [ ! -f "$POM_FILE" ]; then
  echo "Error: No pom.xml found in '$MODULE_PATH'."
  exit 1
fi

# Check if spring-boot-maven-plugin is configured
if ! grep -q "spring-boot-maven-plugin" "$POM_FILE"; then
  echo "Error: spring-boot-maven-plugin is not configured in '$MODULE_NAME'."
  exit 1
fi

# Build the module before running
echo "Building module '$MODULE_NAME' (skip tests)..."
mvn -pl "$MODULE_NAME" -am package -DskipTests -e -s ${WORK_DIR}/.mvn/settings.xml

# Run the Spring Boot application
echo "Running module: $MODULE_NAME"
mvn -pl "$MODULE_NAME" spring-boot:run -e -s ${WORK_DIR}/.mvn/settings.xml
