#!/bin/bash

echo "--- Preparando GlassFish para Render ---"

# 1. Iniciar GlassFish temporalmente para cambiar configuraciones
/usr/local/glassfish4/bin/asadmin start-domain

# 2. Desactivar IIOP (esto quita el spam de certificados SSL caducados y acelera el arranque)
/usr/local/glassfish4/bin/asadmin set configs.config.server-config.iiop-service.iiop-listener.iiop-listener1.enabled=false || true
/usr/local/glassfish4/bin/asadmin set configs.config.server-config.iiop-service.enabled=false || true
/usr/local/glassfish4/bin/asadmin set configs.config.server-config.admin-service.das-config.dynamic-reload-enabled=true || true

# 3. Apagar el servidor temporal
/usr/local/glassfish4/bin/asadmin stop-domain

echo "--- Iniciando GlassFish definitivo ---"

# 4. Iniciar GlassFish en primer plano, escuchando en 0.0.0.0 y limitando la memoria a 280MB
# Usamos -Xmx280m para que quepa en los 512MB de Render sin ser asesinado (OOM)
exec /usr/local/glassfish4/bin/asadmin start-domain --verbose -J-Xmx280m -J-XX:+UseSerialGC --host 0.0.0.0