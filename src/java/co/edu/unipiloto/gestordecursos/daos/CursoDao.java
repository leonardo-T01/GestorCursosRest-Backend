package co.edu.unipiloto.gestordecursos.daos;

import co.edu.unipiloto.gestordecursos.entidades.Curso;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CursoDao {

    @PersistenceContext(unitName = "GestorCursosPU")
    private EntityManager em;

    public List<Curso> listarTodos() {
        return em.createQuery("SELECT c FROM Curso c ORDER BY c.nombre", Curso.class).getResultList();
    }

    public void guardar(Curso curso) {
        em.persist(curso);
    }
}