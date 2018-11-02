FROM java:8

MAINTAINER David <user@domain.com>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/application.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/application.jar
