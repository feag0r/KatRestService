package ru.sbrf.umkozo.kat.rest.service;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.umkozo.kat.rest.model.db.KatTypeRatingEntity;
import ru.sbrf.umkozo.kat.rest.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Service("katTypeRatingService")
@Transactional
public class KatTypeRatingService implements IKatTypeRatingService {

    public KatTypeRatingEntity findById(int id) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            //KatTypeRatingEntity typeRatingEntity = entityManager.find(KatTypeRatingEntity.class, id);
            // EntityManager.find throws NullPointerException, in this particular case, if there is no records
            // with provided id. I don't know why ```(
            // So let's find our entity with query:
            Query query = entityManager.createQuery("select r from KatTypeRatingEntity r where r.id = :id");
            query.setParameter("id", id);
            KatTypeRatingEntity typeRatingEntity = (KatTypeRatingEntity) query.getSingleResult();

            // Force to load a collections, for we have "Lazy Loading" strategy
            typeRatingEntity.getKatRatingsById().size();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (typeRatingEntity.getId()==id)
                return typeRatingEntity;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    // RATING_NAME supposed to be unique, so return only one entity
    public KatTypeRatingEntity findByTypeRatingName(String typeRatingName) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createQuery("select r from KatTypeRatingEntity r where r.ratingName = :ratingName");
            q.setParameter("ratingName", typeRatingName);
            KatTypeRatingEntity typeRatingEntity = (KatTypeRatingEntity) q.getSingleResult();

            // Force to load a collections, for we have "Lazy Loading" strategy
            typeRatingEntity.getKatRatingsById().size();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (typeRatingEntity.getRatingName().equals(typeRatingName))
                return typeRatingEntity;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    public void saveTypeRating(KatTypeRatingEntity typeRating) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(typeRating);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateTypeRating(KatTypeRatingEntity typeRating) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(typeRating);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteTypeRatingById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        KatTypeRatingEntity deleteTypeRatingEntity = entityManager.find(KatTypeRatingEntity.class, id);
        if (deleteTypeRatingEntity != null)
            entityManager.remove(deleteTypeRatingEntity);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @SuppressWarnings("unchecked")
    public List<KatTypeRatingEntity> findAllTypeRatings() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        List<KatTypeRatingEntity> typeRatingEntityList = entityManager.createQuery("select u from KatTypeRatingEntity u").getResultList();

        // Force to load a collections, for we have "Lazy Loading" strategy
        for(KatTypeRatingEntity typeRating : typeRatingEntityList){
            typeRating.getKatRatingsById().size();
        }

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!typeRatingEntityList.isEmpty())
            return typeRatingEntityList;
        else
            return null;
    }

    public void deleteAllTypeRatings() {
        throw new NotYetImplementedException();
    }

    public boolean isTypeRatingExist(KatTypeRatingEntity typeRating) {
        return findByTypeRatingName(typeRating.getRatingName())!=null;
    }
}
