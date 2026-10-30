FROM ubuntu:22.04

# Instalamos Java 8, curl, unzip y bash
RUN apt-get update && apt-get install -y openjdk-8-jdk curl unzip bash && rm -rf /var/lib/apt/lists/*

# Descargamos y descomprimimos GlassFish
RUN curl -L -o /tmp/glassfish-4.1.zip http://download.java.net/glassfish/4.1/release/glassfish-4.1.zip && \
    unzip /tmp/glassfish-4.1.zip -d /usr/local && \
    rm /tmp/glassfish-4.1.zip

# Descargamos el driver de PostgreSQL
RUN curl -L -o /usr/local/glassfish4/glassfish/domains/domain1/lib/postgresql-42.5.0.jar https://jdbc.postgresql.org/download/postgresql-42.5.0.jar

# Copiamos tu archivo WAR
COPY dist/GestorCursosRest.war /usr/local/glassfish4/glassfish/domains/domain1/autodeploy/

# Copiamos el script de arranque y le damos permisos de ejecución
COPY start.sh /usr/local/bin/start.sh
RUN chmod +x /usr/local/bin/start.sh

# Exponemos el puerto
EXPOSE 8080

# Ejecutamos el script
CMD ["/usr/local/bin/start.sh"]