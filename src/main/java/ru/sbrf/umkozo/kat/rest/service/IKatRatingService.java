package ru.sbrf.umkozo.kat.rest.service;

import ru.sbrf.umkozo.kat.rest.model.db.KatRatingEntity;

import java.util.List;

public interface IKatRatingService {
    KatRatingEntity findById(int id);

    KatRatingEntity findByCommentText(String comment);

    List<KatRatingEntity> findByUserId(int userId);

    List<KatRatingEntity> findByQuestionId(int questionId);

    List<KatRatingEntity> findByTypeRatingId(int typeRatingId);

    void saveRating(KatRatingEntity rating);

    void updateRating(KatRatingEntity rating);

    void deleteRatingById(int id);

    List<KatRatingEntity> findAllRatings();

    void deleteAllRatings();

    boolean isRatingExist(KatRatingEntity rating);
}
