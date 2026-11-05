package co.edu.unipiloto.gestorcursos.repository;

import co.edu.unipiloto.gestorcursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    // Aquí podemos agregar búsquedas personalizadas luego, ej:
    // List<Curso> findByProfesorId(Integer profesorId);
}