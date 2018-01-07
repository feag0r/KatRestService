package ru.sbrf.umkozo.kat.rest.service;

import ru.sbrf.umkozo.kat.rest.model.db.KatQuestionEntity;

import java.util.List;

public interface IKatQuestionService {
    KatQuestionEntity findById(int id);

    KatQuestionEntity findByQuestionText(String questionText);

    List<KatQuestionEntity> findByUserId(int userId);

    List<KatQuestionEntity> findBySubjectId(int subjectId);

    void saveQuestion(KatQuestionEntity question);

    void updateQuestion(KatQuestionEntity question);

    void deleteQuestionById(int id);

    List<KatQuestionEntity> findAllQuestions();

    void deleteAllQuestions();

    boolean isQuestionExist(KatQuestionEntity question);
}
