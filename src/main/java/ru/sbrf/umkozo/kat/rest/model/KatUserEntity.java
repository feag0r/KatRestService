package ru.sbrf.umkozo.kat.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "KAT_USER", schema = "C##KAT")
public class KatUserEntity {
    private int id;
    private String login;
    private String fullName;
    private Collection<KatQuestionEntity> katQuestionsById;
    private Collection<KatRatingEntity> katRatingsById;

    public KatUserEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId_generator")
    @SequenceGenerator(name="userId_generator", sequenceName = "KAT_USER_SEQ", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "LOGIN", nullable = false, length = 4000)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "FULL_NAME", nullable = true, length = 4000)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @OneToMany(mappedBy = "katUserByIdUser")
    @JsonIgnore
    public Collection<KatQuestionEntity> getKatQuestionsById() {
        return katQuestionsById;
    }

    public void setKatQuestionsById(Collection<KatQuestionEntity> katQuestionsById) {
        this.katQuestionsById = katQuestionsById;
    }

    @OneToMany(mappedBy = "katUserByIdUser")
    @JsonIgnore
    public Collection<KatRatingEntity> getKatRatingsById() {
        return katRatingsById;
    }

    public void setKatRatingsById(Collection<KatRatingEntity> katRatingsById) {
        this.katRatingsById = katRatingsById;
    }
}
