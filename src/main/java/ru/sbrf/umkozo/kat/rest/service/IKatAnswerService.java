package ru.sbrf.umkozo.kat.rest.service;

import ru.sbrf.umkozo.kat.rest.model.db.KatAnswerEntity;

import java.util.List;

public interface IKatAnswerService {
    KatAnswerEntity findById(int id);

    KatAnswerEntity findByAnswerText(String answerText);

    List<KatAnswerEntity> findByQuestionId(int questionId);

    void saveAnswer(KatAnswerEntity answer);

    void updateAnswer(KatAnswerEntity answer);

    void deleteAnswerById(int id);

    List<KatAnswerEntity> findAllAnswers();

    void deleteAllAnswers();

    boolean isAnswerExist(KatAnswerEntity answer);
}
