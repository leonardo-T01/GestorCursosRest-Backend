package co.edu.unipiloto.gestorcursos.service;

import co.edu.unipiloto.gestorcursos.model.Usuario;
import co.edu.unipiloto.gestorcursos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerProfesoresPendientes() {
        return usuarioRepository.findByRolAndEstadoAprobacion("PROFESOR", "PENDIENTE");
    }

    public boolean aprobarProfesor(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null && "PROFESOR".equals(usuario.getRol())) {
            usuario.setEstadoAprobacion("APROBADO");
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}