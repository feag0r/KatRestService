package ru.sbrf.umkozo.kat.rest.service;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.umkozo.kat.rest.model.db.KatSubjectQuestionEntity;
import ru.sbrf.umkozo.kat.rest.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Service("katSubjectQuestionService")
@Transactional
public class KatSubjectQuestionService implements IKatSubjectQuestionService {

    public KatSubjectQuestionEntity findById(int id) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            //KatSubjectQuestionEntity subjectQuestionEntity = entityManager.find(KatSubjectQuestionEntity.class, id);
            // EntityManager.find throws NullPointerException, in this particular case, if there is no records
            // with provided id. I don't know why ```(
            // So let's find our entity with query:
            Query query = entityManager.createQuery("select r from KatSubjectQuestionEntity r where r.id = :id");
            query.setParameter("id", id);
            KatSubjectQuestionEntity subjectQuestionEntity = (KatSubjectQuestionEntity) query.getSingleResult();

            // Force to load a collections, for we have "Lazy Loading" strategy
            subjectQuestionEntity.getKatQuestionsById().size();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (subjectQuestionEntity.getId()==id)
                return subjectQuestionEntity;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    // SUBJECT supposed to be unique, so return only one entity
    public KatSubjectQuestionEntity findBySubjectName(String subject) {
        try {
            EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createQuery("select r from KatSubjectQuestionEntity r where r.subject = :subject");
            q.setParameter("subject", subject);
            KatSubjectQuestionEntity subjectQuestionEntity = (KatSubjectQuestionEntity) q.getSingleResult();

            // Force to load a collections, for we have "Lazy Loading" strategy
            subjectQuestionEntity.getKatQuestionsById().size();

            entityManager.getTransaction().commit();
            entityManager.close();

            if (subjectQuestionEntity.getSubject().equals(subject))
                return subjectQuestionEntity;
            else
                return null;
        }
        catch (NoResultException e){
            return null;
        }
    }

    public void saveSubjectQuestion(KatSubjectQuestionEntity subjectQuestion) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(subjectQuestion);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateSubjectQuestion(KatSubjectQuestionEntity subjectQuestion) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(subjectQuestion);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteSubjectQuestionById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        KatSubjectQuestionEntity deleteSubjectQuestionEntity = entityManager.find(KatSubjectQuestionEntity.class, id);
        if (deleteSubjectQuestionEntity != null)
            entityManager.remove(deleteSubjectQuestionEntity);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @SuppressWarnings("unchecked")
    public List<KatSubjectQuestionEntity> findAllSubjectQuestions() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        List<KatSubjectQuestionEntity> subjectQuestionEntityList = entityManager.createQuery("select u from KatSubjectQuestionEntity u").getResultList();

        // Force to load a collections, for we have "Lazy Loading" strategy
        for(KatSubjectQuestionEntity subjectQuestion : subjectQuestionEntityList){
            subjectQuestion.getKatQuestionsById().size();
        }

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!subjectQuestionEntityList.isEmpty())
            return subjectQuestionEntityList;
        else
            return null;
    }

    public void deleteAllSubjectQuestions() {
        throw new NotYetImplementedException();
    }

    public boolean isSubjectQuestionExist(KatSubjectQuestionEntity subjectQuestion) {
        return findBySubjectName(subjectQuestion.getSubject())!=null;
    }
}
