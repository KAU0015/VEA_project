package cz.vsb.vea.project.repositories.JPA;

import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.repositories.UserRepositoryInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.username = :username", User.class);

        List<User> users = query.setParameter("username", username).getResultList();
        if(users.isEmpty())
            return null;
        return users.get(0);
    }

    @Override
    public List<User> getAllUsersNoWithId(long id) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.id != :id", User.class);

        return query.setParameter("id", id).getResultList();
    }

    @Override
    public List<User> getAllUsersNoWithId(long id, String name) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.id != :id and u.username like concat('%',:name,'%')", User.class);

        return query.setParameter("id", id).setParameter("name", name).getResultList();
    }
}
