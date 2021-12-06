package cz.vsb.vea.project.repositories.JPA;

import cz.vsb.vea.project.models.Comment;
import cz.vsb.vea.project.repositories.CommentRepositoryInterface;
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
public class CommentRepositoryJpa implements CommentRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> getAllComments() {
        return em.createQuery("select * from Comment c", Comment.class).getResultList();
    }

    @Transactional
    @Override
    public Comment save(Comment c) {
        if (c.getId() == 0) {
            em.persist(c);
        } else {
            c = em.merge(c);
        }
        return c;
    }

    @Override
    public Comment find(long id) {
        return em.find(Comment.class, (int)id);
    }
}
