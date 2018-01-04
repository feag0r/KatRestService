package ru.sbrf.umkozo.kat.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.umkozo.kat.rest.model.KatUserEntity;
import ru.sbrf.umkozo.kat.rest.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Service("katUserService")
@Transactional
public class KatUserService implements IKatUserService {

    public KatUserEntity findById(int id) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            //KatUserEntity userEntity = entityManager.find(KatUserEntity.class, id);
            // EntityManager.find throws NullPointerException, in this particular case, if there is no records
            // with provided id. I don't know why ```(
            // So let's find our entity with query:
            Query query = entityManager.createQuery("select u from KatUserEntity u where u.id = :id");
            query.setParameter("id", id);
            KatUserEntity userEntity = (KatUserEntity) query.getSingleResult();

            // Force to load a collections, for we have "Lazy Loading" strategy
            userEntity.getKatRatingsById().size();
            userEntity.getKatQuestionsById().size();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (userEntity.getId()==id)
                return userEntity;
            else
                return null;

        }
        catch (NoResultException e){
            return null;
        }
    }

    // Perhaps we have some namesakes, so using List here for return entities
    @SuppressWarnings("unchecked")
    public List<KatUserEntity> findByFullName(String fullName) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Query q = entityManager.createQuery("select u from KatUserEntity u where u.fullName = :fn");
        q.setParameter("fn", fullName);
        List<KatUserEntity> userEntityList = q.getResultList();

        // Force to load a collections, for we have "Lazy Loading" strategy
        for(KatUserEntity user : userEntityList){
            user.getKatRatingsById().size();
            user.getKatQuestionsById().size();
        }

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!userEntityList.isEmpty())
            return userEntityList;
        else
            return null;
    }

    // LOGIN supposed to be unique, so return only one entity
    public KatUserEntity findByLogin(String login) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createQuery("select u from KatUserEntity u where u.login = :login");
            q.setParameter("login", login);
            KatUserEntity userEntity = (KatUserEntity) q.getSingleResult();

            // Force to load a collections, for we have "Lazy Loading" strategy
            userEntity.getKatRatingsById().size();
            userEntity.getKatQuestionsById().size();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (userEntity.getLogin().equals(login))
                return userEntity;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    public void saveUser(KatUserEntity user) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateUser(KatUserEntity user) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteUserById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        KatUserEntity deleteUser = entityManager.find(KatUserEntity.class, id);
        if (deleteUser != null)
            entityManager.remove(deleteUser);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @SuppressWarnings("unchecked")
    public List<KatUserEntity> findAllUsers() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        List<KatUserEntity> userEntityList = entityManager.createQuery("select u from KatUserEntity u").getResultList();

        // Force to load a collections, for we have "Lazy Loading" strategy
        for(KatUserEntity user : userEntityList){
            user.getKatRatingsById().size();
            user.getKatQuestionsById().size();
        }

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!userEntityList.isEmpty())
            return userEntityList;
        else
            return null;
    }

    public void deleteAllUsers() {

    }

    public boolean isUserExist(KatUserEntity user) {
        return findByLogin(user.getLogin())!=null;
    }
}
