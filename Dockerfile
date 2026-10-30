FROM ubuntu:22.04

RUN apt-get update && apt-get install -y openjdk-8-jdk curl unzip && rm -rf /var/lib/apt/lists/*

# Descargamos GlassFish
RUN curl -L -o /tmp/glassfish-4.1.zip http://download.java.net/glassfish/4.1/release/glassfish-4.1.zip && \
    unzip /tmp/glassfish-4.1.zip -d /usr/local && \
    rm /tmp/glassfish-4.1.zip

# NUEVO: Descargamos el driver JDBC de PostgreSQL y lo ponemos en la carpeta lib de GlassFish
RUN curl -L -o /usr/local/glassfish4/glassfish/domains/domain1/lib/postgresql-42.5.0.jar https://jdbc.postgresql.org/download/postgresql-42.5.0.jar

COPY dist/GestorCursosRest.war /usr/local/glassfish4/glassfish/domains/domain1/autodeploy/

EXPOSE 8080

CMD ["/usr/local/glassfish4/bin/asadmin", "start-domain", "--verbose"]