package co.edu.unipiloto.gestordecursos.seguridad;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class JwtFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Ignoramos la seguridad si es para el Login o Registro
        String path = requestContext.getUriInfo().getPath();
        if (path.contains("auth/login") || path.contains("auth/registro")) {
            return;
        }

        // Obtener el header "Authorization"
        String authHeader = requestContext.getHeaderString("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token no proporcionado\"}").build());
            return;
        }

        // Extraer el token (quitando la palabra "Bearer ")
        String token = authHeader.substring(7);
        String correo = JwtUtils.validarTokenYObtenerCorreo(token);

        if (correo == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token inválido o expirado\"}").build());
        }
    }
}