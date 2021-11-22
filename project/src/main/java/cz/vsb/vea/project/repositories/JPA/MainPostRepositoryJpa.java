package cz.vsb.vea.project.repositories.JPA;

import cz.vsb.vea.project.models.MainPost;
import cz.vsb.vea.project.repositories.MainPostRepositoryInterface;
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
public class MainPostRepositoryJpa implements MainPostRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<MainPost> getAllMainPosts() {
        return em.createQuery("select mp from MainPost mp order by mp.date desc", MainPost.class).getResultList();
    }

    @Transactional
    @Override
    public MainPost save(MainPost mp) {
        if (mp.getId() == 0) {
            em.persist(mp);
        } else {
            mp = em.merge(mp);
        }
        return mp;
    }

    @Override
    public MainPost find(long id) {
        return em.find(MainPost.class, id);
    }
}
