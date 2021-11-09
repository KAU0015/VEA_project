package cz.vsb.vea.project.repositories.JPA;

import cz.vsb.vea.project.models.Post;
import cz.vsb.vea.project.repositories.PostRepositoryInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
