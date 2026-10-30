# Usamos una imagen base de Ubuntu
FROM ubuntu:22.04

# Instalamos Java 8 y curl (para descargar GlassFish)
RUN apt-get update && apt-get install -y openjdk-8-jdk wget unzip && rm -rf /var/lib/apt/lists/*

# Descargamos y descomprimimos GlassFish 4.1
RUN wget http://dlc-cdn.sun.com/glassfish/4.1/release/glassfish-4.1.zip && \
    unzip glassfish-4.1.zip && \
    rm glassfish-4.1.zip

# Copiamos el archivo .war de tu proyecto al directorio de autodeploy de GlassFish
# Asegúrate de que el nombre del archivo .war coincida con el que genera NetBeans
COPY dist/GestorCursosRest.war /glassfish4/glassfish/domains/domain1/autodeploy/

# Exponemos el puerto 8080
EXPOSE 8080

# Comando para iniciar GlassFish
CMD ["/glassfish4/bin/asadmin", "start-domain", "--verbose"]