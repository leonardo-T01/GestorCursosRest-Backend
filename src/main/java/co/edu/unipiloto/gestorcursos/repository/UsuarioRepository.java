package co.edu.unipiloto.gestorcursos.repository;

import co.edu.unipiloto.gestorcursos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    
    // NUEVO:
    List<Usuario> findByRolAndEstadoAprobacion(String rol, String estadoAprobacion);
}