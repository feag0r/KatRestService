package ru.sbrf.umkozo.kat.rest.service;

import ru.sbrf.umkozo.kat.rest.model.db.KatUserEntity;

import java.util.List;

public interface IKatUserService {
    KatUserEntity findById(int id);

    List<KatUserEntity> findByFullName(String fullName);

    KatUserEntity findByLogin(String login);

    void saveUser(KatUserEntity user);

    void updateUser(KatUserEntity user);

    void deleteUserById(int id);

    List<KatUserEntity> findAllUsers();

    void deleteAllUsers();

    boolean isUserExist(KatUserEntity user);
}
