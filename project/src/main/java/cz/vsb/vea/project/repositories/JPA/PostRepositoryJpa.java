package cz.vsb.vea.project.repositories.JPA;

import cz.vsb.vea.project.models.Post;
import cz.vsb.vea.project.repositories.PostRepositoryInterface;
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
public class PostRepositoryJpa implements PostRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Post> getAllPosts() {
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }

    @Transactional
    @Override
    public Post save(Post p) {
        if (p.getId() == 0) {
            em.persist(p);
        } else {
            p = em.merge(p);
        }
        return p;
    }

    @Override
    public Post find(long id) {
        return em.find(Post.class, id);
    }

    @Override
    public List<Post> find10LastPosts(long userId) {
        TypedQuery<Post> query = em.createQuery("select p from Post p where p.user.id = :userId order by p.date desc", Post.class).setMaxResults(10);
        return query.setParameter("userId", userId).getResultList();
    }
}
