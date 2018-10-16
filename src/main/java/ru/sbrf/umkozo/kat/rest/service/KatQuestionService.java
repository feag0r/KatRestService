package ru.sbrf.umkozo.kat.rest.service;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.umkozo.kat.rest.model.db.KatQuestionEntity;
import ru.sbrf.umkozo.kat.rest.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Service("katQuestionService")
@Transactional
public class KatQuestionService implements IKatQuestionService {
    public KatQuestionEntity findById(int id) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            //KatQuestionEntity question = entityManager.find(KatQuestionEntity.class, id);
            // EntityManager.find throws NullPointerException, in this particular case, if there is no records
            // with provided id. I don't know why ```(
            // So let's find our entity with query:
            Query query = entityManager.createQuery("select r from KatQuestionEntity r where r.id = :id");
            query.setParameter("id", id);
            KatQuestionEntity question = (KatQuestionEntity) query.getSingleResult();

            // Force to load a collections, for we have "Lazy Loading" strategy
            question.getKatAnswersById().size();
            question.getKatRatingsById().size();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (question.getId()==id)
                return question;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    // QUESTION supposed to be unique, so return only one entity
    public KatQuestionEntity findByQuestionText(String questionText) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createQuery("select u from KatQuestionEntity u where u.question = :questionText");
            q.setParameter("questionText", questionText);
            KatQuestionEntity question = (KatQuestionEntity) q.getSingleResult();

            // Force to load a collections, for we have "Lazy Loading" strategy
            question.getKatAnswersById().size();
            question.getKatRatingsById().size();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (question.getQuestion().equals(questionText))
                return question;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<KatQuestionEntity> findByUserId(int userId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Query q = entityManager.createQuery("select u from KatQuestionEntity u where u.katUserByIdUser.id = :id");
        q.setParameter("id", userId);
        List<KatQuestionEntity> questionsList = q.getResultList();

        // Force to load a collections, for we have "Lazy Loading" strategy
        for(KatQuestionEntity question : questionsList){
            question.getKatAnswersById().size();
            question.getKatRatingsById().size();
        }

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!questionsList.isEmpty())
            return questionsList;
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    public List<KatQuestionEntity> findBySubjectId(int subjectId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Query q = entityManager.createQuery("select u from KatQuestionEntity u where u.katSubjectQuestionByIdSubject.id = :subjectId");
        q.setParameter("subjectId", subjectId);
        List<KatQuestionEntity> questionsList = q.getResultList();

        // Force to load a collections, for we have "Lazy Loading" strategy
        for(KatQuestionEntity question : questionsList){
            question.getKatAnswersById().size();
            question.getKatRatingsById().size();
        }

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!questionsList.isEmpty())
            return questionsList;
        else
            return null;
    }

    public void saveQuestion(KatQuestionEntity question) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(question);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateQuestion(KatQuestionEntity question) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(question);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteQuestionById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        KatQuestionEntity deleteQuestionEntity = entityManager.find(KatQuestionEntity.class, id);
        if (deleteQuestionEntity != null)
            entityManager.remove(deleteQuestionEntity);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @SuppressWarnings("unchecked")
    public List<KatQuestionEntity> findAllQuestions() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        List<KatQuestionEntity> questionEntityList = entityManager.createQuery("select u from KatQuestionEntity u").getResultList();

        // Force to load a collections, for we have "Lazy Loading" strategy
        for(KatQuestionEntity question : questionEntityList){
            question.getKatAnswersById().size();
            question.getKatRatingsById().size();
        }

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!questionEntityList.isEmpty())
            return questionEntityList;
        else
            return null;
    }

    public void deleteAllQuestions() {
        throw new NotYetImplementedException();
    }

    public boolean isQuestionExist(KatQuestionEntity question) {
        return findByQuestionText(question.getQuestion())!=null;
    }
}
