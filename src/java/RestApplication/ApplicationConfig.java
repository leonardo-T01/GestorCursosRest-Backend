package RestApplication;

import java.util.Set;
import javax.ws.rs.core.Application;
// Importamos los servicios para que sea más limpio el código
import co.edu.unipiloto.gestordecursos.servicios.CursoService;
import co.edu.unipiloto.gestordecursos.servicios.AuthService;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    // SOLO DEBE HABER UN MÉTODO addRestResourceClasses
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.edu.unipiloto.gestordecursos.seguridad.JwtFilter.class);
        resources.add(co.edu.unipiloto.gestordecursos.servicios.AuthService.class);
        resources.add(co.edu.unipiloto.gestordecursos.servicios.CursoService.class);
    }
}