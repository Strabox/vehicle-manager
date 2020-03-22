#ARG BASE_IMAGE=arm32v7/openjdk:8-jdk
#FROM openjdk:8-jdk
FROM --platform=linux/arm/v7 arm32v7/openjdk:8-jdk

LABEL author="André Pires" version="1.0-dev"

ARG APP_GROUP="vehicle-mgr"
ARG APP_USER="vehicle-mgr"
ARG APP_FILES_DIR="aux-files"

# Use specific user (to avoid run as root container) to do not have extra privilleges
RUN addgroup --gid 150 ${APP_USER} && adduser --uid 1050 --ingroup ${APP_GROUP} ${APP_USER}
USER ${APP_GROUP}:${APP_USER}

WORKDIR "/home/${APP_USER}"
# Create a directory for the auxiliary files of the WebServer
RUN mkdir ${APP_FILES_DIR}

ARG JAR_FILE=vehicle-manager*.jar
# Use chown to give that files to user instead of root
COPY --chown=${APP_USER}:${APP_GROUP} ${JAR_FILE} app.jar

# WebServer port
EXPOSE 8080/TCP

CMD echo "Welcome to Vehicle Manager Docker Container..."
ENTRYPOINT ["java", "-jar", "app.jar"]