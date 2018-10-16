package ru.sbrf.umkozo.kat.rest.service;

import ru.sbrf.umkozo.kat.rest.model.db.KatSubjectQuestionEntity;

import java.util.List;

public interface IKatSubjectQuestionService {
    KatSubjectQuestionEntity findById(int id);

    KatSubjectQuestionEntity findBySubjectName(String subject);

    void saveSubjectQuestion(KatSubjectQuestionEntity subjectQuestion);

    void updateSubjectQuestion(KatSubjectQuestionEntity subjectQuestion);

    void deleteSubjectQuestionById(int id);

    List<KatSubjectQuestionEntity> findAllSubjectQuestions();

    void deleteAllSubjectQuestions();

    boolean isSubjectQuestionExist(KatSubjectQuestionEntity subjectQuestion);
}
