package co.edu.unipiloto.gestorcursos.controller;

import co.edu.unipiloto.gestorcursos.model.Curso;
import co.edu.unipiloto.gestorcursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*") // Permite que tu app Android se conecte sin problemas
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // GET: http://localhost:8080/api/cursos
    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.obtenerTodos();
    }

        // POST: http://localhost:8080/api/cursos
    @PostMapping
    public Curso crearCurso(@RequestBody Curso curso) {
        // Aquí podrías validar que el profesorId exista y esté APROBADO
        return cursoService.guardar(curso);
    }
}