package co.edu.unipiloto.gestordecursos.daos;

import co.edu.unipiloto.gestordecursos.entidades.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

@Stateless
public class UsuarioDao {

    @PersistenceContext(unitName = "GestorCursosPU")
    private EntityManager em;

    public void guardar(Usuario usuario) {
        em.persist(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo", Usuario.class)
                     .setParameter("correo", correo)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null si el usuario no existe
        }
    }
}