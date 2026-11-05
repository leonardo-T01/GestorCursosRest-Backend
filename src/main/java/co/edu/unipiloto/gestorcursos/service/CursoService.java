package co.edu.unipiloto.gestorcursos.service;

import co.edu.unipiloto.gestorcursos.model.Curso;
import co.edu.unipiloto.gestorcursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> obtenerTodos() {
        return cursoRepository.findAll();
    }

    public Curso guardar(Curso curso) {
        // SI EL ID ES 0 O NULL, LO FORZAMOS A NULL PARA QUE HIBERNATE HAGA UN INSERT
        if (curso.getId() != null && curso.getId() == 0) {
            curso.setId(null);
        }
        return cursoRepository.save(curso);
    }
}