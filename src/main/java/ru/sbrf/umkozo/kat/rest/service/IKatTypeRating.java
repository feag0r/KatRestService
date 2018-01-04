package ru.sbrf.umkozo.kat.rest.service;

import ru.sbrf.umkozo.kat.rest.model.KatTypeRatingEntity;

import java.util.List;

public interface IKatTypeRating {
    KatTypeRatingEntity findById(int id);

    KatTypeRatingEntity findByTypeRatingName(String typeRatingName);

    void saveTypeRating(KatTypeRatingEntity typeRating);

    void updateTypeRating(KatTypeRatingEntity typeRating);

    void deleteTypeRatingById(int id);

    List<KatTypeRatingEntity> findAllTypeRatings();

    void deleteAllTypeRatings();

    boolean isTypeRatingExist(KatTypeRatingEntity typeRating);
}