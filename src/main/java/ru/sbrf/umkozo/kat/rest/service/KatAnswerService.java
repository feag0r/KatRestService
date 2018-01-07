package ru.sbrf.umkozo.kat.rest.service;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.umkozo.kat.rest.model.db.KatAnswerEntity;
import ru.sbrf.umkozo.kat.rest.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Service("katAnswerService")
@Transactional
public class KatAnswerService implements IKatAnswerService {
    public KatAnswerEntity findById(int id) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            //KatAnswerEntity answer = entityManager.find(KatAnswerEntity.class, id);
            // EntityManager.find throws NullPointerException, in this particular case, if there is no records
            // with provided id. I don't know why ```(
            // So let's find our entity with query:
            Query query = entityManager.createQuery("select r from KatAnswerEntity r where r.id = :id");
            query.setParameter("id", id);
            KatAnswerEntity answer = (KatAnswerEntity) query.getSingleResult();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (answer.getId()==id)
                return answer;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    // ANSWER supposed to be unique, so return only one entity
    public KatAnswerEntity findByAnswerText(String answerText) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createQuery("select u from KatAnswerEntity u where u.answer = :answerText");
            q.setParameter("answerText", answerText);
            KatAnswerEntity answer = (KatAnswerEntity) q.getSingleResult();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (answer.getAnswer().equals(answerText))
                return answer;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    public List<KatAnswerEntity> findByQuestionId(int questionId) {
        // TODO: Implement Answers finding by Question Id
        return null;
    }

    public void saveAnswer(KatAnswerEntity answer) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(answer);

        entityManager.getTransaction().commit();
        entityManager.close();    }

    public void updateAnswer(KatAnswerEntity answer) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(answer);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteAnswerById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        KatAnswerEntity deleteAnswerEntity = entityManager.find(KatAnswerEntity.class, id);
        if (deleteAnswerEntity != null)
            entityManager.remove(deleteAnswerEntity);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @SuppressWarnings("unchecked")
    public List<KatAnswerEntity> findAllAnswers() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        List<KatAnswerEntity> answerEntityList = entityManager.createQuery("select u from KatAnswerEntity u").getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!answerEntityList.isEmpty())
            return answerEntityList;
        else
            return null;
    }

    public void deleteAllAnswers() {
        throw new NotYetImplementedException();
    }

    public boolean isAnswerExist(KatAnswerEntity answer) {
        return findByAnswerText(answer.getAnswer())!=null;
    }
}
