package co.edu.unipiloto.gestordecursos.servicios;

import co.edu.unipiloto.gestordecursos.daos.UsuarioDao;
import co.edu.unipiloto.gestordecursos.entidades.Usuario;
import co.edu.unipiloto.gestordecursos.dto.LoginResponse;
import co.edu.unipiloto.gestordecursos.seguridad.JwtUtils;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthService {

    @EJB
    private UsuarioDao usuarioDao;

    @POST
    @Path("/registro")
    public Response registrar(Usuario usuario) {
        if (usuarioDao.buscarPorCorreo(usuario.getCorreo()) != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"El correo ya está registrado\"}").build();
        }

        String passwordHash = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
        usuario.setPassword(passwordHash);
        usuario.setVerificado(true); 

        usuarioDao.guardar(usuario);

        return Response.status(Response.Status.CREATED)
                .entity("{\"mensaje\":\"Usuario registrado exitosamente\"}").build();
    }

    @POST
    @Path("/login")
    public Response login(Usuario credenciales) {
        Usuario usuarioDB = usuarioDao.buscarPorCorreo(credenciales.getCorreo());

        if (usuarioDB == null || !BCrypt.checkpw(credenciales.getPassword(), usuarioDB.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Credenciales incorrectas\"}").build();
        }

        // Generamos el token JWT
        String token = JwtUtils.generarToken(usuarioDB.getCorreo(), usuarioDB.getRol());

        // SEGURIDAD: Ocultamos la contraseña antes de enviar la respuesta
        usuarioDB.setPassword(null); 

        LoginResponse respuesta = new LoginResponse(token, usuarioDB);

        return Response.ok(respuesta).build();
    }
}