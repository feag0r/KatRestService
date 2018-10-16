package ru.sbrf.umkozo.kat.rest.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "KAT_QUESTION", schema = "C##KAT")
public class KatQuestionEntity {
    private int id;
    private java.sql.Date dateCreate;
    private String question;
    private Collection<KatAnswerEntity> katAnswersById;
    private KatUserEntity katUserByIdUser;
    private KatSubjectQuestionEntity katSubjectQuestionByIdSubject;
    private Collection<KatRatingEntity> katRatingsById;

    public KatQuestionEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionId_generator")
    @SequenceGenerator(name="questionId_generator", sequenceName = "KAT_QUESTION_SEQ", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DATE_CREATE", nullable = false)
    public java.sql.Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(java.sql.Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Basic
    @Column(name = "QUESTION", nullable = false, length = 4000)
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @OneToMany(mappedBy = "katQuestionByIdQuestion")
    @JsonIgnore
    public Collection<KatAnswerEntity> getKatAnswersById() {
        return katAnswersById;
    }

    public void setKatAnswersById(Collection<KatAnswerEntity> katAnswersById) {
        this.katAnswersById = katAnswersById;
    }

    @ManyToOne
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID", nullable = false)
    public KatUserEntity getKatUserByIdUser() {
        return katUserByIdUser;
    }

    public void setKatUserByIdUser(KatUserEntity katUserByIdUser) {
        this.katUserByIdUser = katUserByIdUser;
    }

    @ManyToOne
    @JoinColumn(name = "ID_SUBJECT", referencedColumnName = "ID", nullable = false)
    public KatSubjectQuestionEntity getKatSubjectQuestionByIdSubject() {
        return katSubjectQuestionByIdSubject;
    }

    public void setKatSubjectQuestionByIdSubject(KatSubjectQuestionEntity katSubjectQuestionByIdSubject) {
        this.katSubjectQuestionByIdSubject = katSubjectQuestionByIdSubject;
    }

    @OneToMany(mappedBy = "katQuestionByIdQuestion")
    @JsonIgnore
    public Collection<KatRatingEntity> getKatRatingsById() {
        return katRatingsById;
    }

    public void setKatRatingsById(Collection<KatRatingEntity> katRatingsById) {
        this.katRatingsById = katRatingsById;
    }
}

