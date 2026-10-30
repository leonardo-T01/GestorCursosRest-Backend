package co.edu.unipiloto.gestordecursos.servicios;

import co.edu.unipiloto.gestordecursos.entidades.Curso;
import co.edu.unipiloto.gestordecursos.daos.CursoDao;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CursoService {

    @EJB
    private CursoDao cursoDao;

    @GET
    public List<Curso> listar() {
        return cursoDao.listarTodos();
    }

    @POST
    public Response crear(Curso curso) {
        // FIX: Los métodos getters llevan paréntesis al final: getNombre() y getDescripcion()
        System.out.println("Recibido: " + curso.getNombre() + " - " + curso.getDescripcion());
        
        cursoDao.guardar(curso);
        
        return Response.status(Response.Status.CREATED).entity(curso).build();
    }
}