package cz.vsb.vea.project.repositories.JPA;

import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.repositories.UserRepositoryInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@ConditionalOnProperty(
        value="accessType",
        havingValue = "jpa",
        matchIfMissing = false)
public class UserRepositoryJpa implements UserRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Transactional
    @Override
    public User save(User u) {
        if (u.getId() == 0) {
            em.persist(u);
        } else {
            u = em.merge(u);
        }
        return u;
    }

    @Transactional
    @Override
    public User find(long id) {
        return em.find(User.class, id);
    }
}
