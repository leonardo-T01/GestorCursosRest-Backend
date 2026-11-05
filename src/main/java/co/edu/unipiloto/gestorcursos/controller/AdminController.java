package co.edu.unipiloto.gestorcursos.controller;

import co.edu.unipiloto.gestorcursos.model.Usuario;
import co.edu.unipiloto.gestorcursos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    // GET: http://localhost:8080/api/admin/profesores/pendientes
    @GetMapping("/profesores/pendientes")
    public List<Usuario> listarPendientes() {
        return usuarioService.obtenerProfesoresPendientes();
    }

    // PUT: http://localhost:8080/api/admin/profesores/{id}/aprobar
    @PutMapping("/profesores/{id}/aprobar")
    public ResponseEntity<?> aprobar(@PathVariable Long id) {
        boolean exito = usuarioService.aprobarProfesor(id);
        if (exito) {
            return ResponseEntity.ok("Profesor aprobado correctamente");
        }
        return ResponseEntity.badRequest().body("No se pudo aprobar al profesor");
    }
}