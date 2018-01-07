package ru.sbrf.umkozo.kat.rest.service;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.umkozo.kat.rest.model.db.KatRatingEntity;
import ru.sbrf.umkozo.kat.rest.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Service("katRatingService")
@Transactional
public class KatRatingService implements IKatRatingService {
    public KatRatingEntity findById(int id) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            //KatRatingEntity rating = entityManager.find(KatRatingEntity.class, id);
            // EntityManager.find throws NullPointerException, in this particular case, if there is no records
            // with provided id. I don't know why ```(
            // So let's find our entity with query:
            Query query = entityManager.createQuery("select r from KatRatingEntity r where r.id = :id");
            query.setParameter("id", id);
            KatRatingEntity rating = (KatRatingEntity) query.getSingleResult();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (rating.getId()==id)
                return rating;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    // COMMENTS supposed to be unique, so return only one entity
    public KatRatingEntity findByCommentText(String comment) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createQuery("select u from KatRatingEntity u where u.comments = :commentText");
            q.setParameter("commentText", comment);
            KatRatingEntity rating = (KatRatingEntity) q.getSingleResult();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (rating.getComments().equals(comment))
                return rating;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    public List<KatRatingEntity> findByUserId(int userId) {
        // TODO: Implement Ratings finding by User Id
        return null;
    }

    public List<KatRatingEntity> findByQuestionId(int questionId) {
        // TODO: Implement Ratings finding by Question Id
        return null;
    }

    public List<KatRatingEntity> findByTypeRatingId(int typeRatingId) {
        // TODO: Implement Ratings finding by Type Rating Id
        return null;
    }

    public void saveRating(KatRatingEntity rating) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(rating);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateRating(KatRatingEntity rating) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(rating);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteRatingById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        KatRatingEntity deleteRatingEntity = entityManager.find(KatRatingEntity.class, id);
        if (deleteRatingEntity != null)
            entityManager.remove(deleteRatingEntity);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @SuppressWarnings("unchecked")
    public List<KatRatingEntity> findAllRatings() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        List<KatRatingEntity> ratingEntityList = entityManager.createQuery("select u from KatRatingEntity u").getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!ratingEntityList.isEmpty())
            return ratingEntityList;
        else
            return null;
    }

    public void deleteAllRatings() {
        throw new NotYetImplementedException();
    }

    public boolean isRatingExist(KatRatingEntity rating) {
        return findByCommentText(rating.getComments())!=null;
    }
}
