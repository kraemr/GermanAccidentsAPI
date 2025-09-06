FROM alpine:latest



# Install required packages: bash, OpenJDK, MariaDB
RUN apk update && apk add --no-cache \
    openjdk21 \
    bash \
    mariadb mariadb-client \
    curl \
    git \
    python3 \
    py3-requests \
    py3-mysqlclient \
    unzip
    
RUN rm /etc/my.cnf.d/mariadb-server.cnf
COPY mariadb.cnf /etc/my.cnf.d/mariadb.cnf

ENV MYSQL_DATABASE=AccidentDB \
    MYSQL_USER=myuser \
    MYSQL_PASSWORD=Test \
    FISERV_FILE_LOCATION=/opt/


COPY setup.sql /opt/server/setup.sql


RUN mkdir /var/lib/mysql \
    && chown -R mysql:mysql /var/lib/mysql \
    && mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql

RUN mkdir -p /run/mysqld && chown -R mysql:mysql /run/mysqld
RUN chown -R mysql:mysql /var/lib/mysql

# Define Gradle version
ENV GRADLE_VERSION=8.5
ENV GRADLE_HOME=/opt/gradle/gradle-${GRADLE_VERSION}
ENV PATH="${GRADLE_HOME}/bin:${PATH}"

# Install Gradle manually
RUN mkdir -p /opt/gradle \
 && curl -fsSL https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o gradle.zip \
 && unzip gradle.zip -d /opt/gradle \
 && ln -s /opt/gradle/gradle-${GRADLE_VERSION}/bin/gradle /usr/bin/gradle \
 && rm gradle.zip

# Copy Spring Boot app
COPY server/ /opt/server/
WORKDIR /opt/server

# Copy and set permissions for MariaDB entrypoint
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh


# Expose Spring Boot port
EXPOSE 8080

# Entrypoint: run MariaDB init in background, then Spring Boot app
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
CMD ["gradle","--no-daemon", "bootRun"]