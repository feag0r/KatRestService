package ru.sbrf.umkozo.kat.rest.model;

import javax.persistence.*;

@Entity
@Table(name = "KAT_RATING", schema = "C##KAT")
public class KatRatingEntity {
    private int id;
    private java.sql.Date dateRating;
    private String comments;
    private KatUserEntity katUserByIdUser;
    private KatQuestionEntity katQuestionByIdQuestion;
    private KatTypeRatingEntity katTypeRatingByIdTypeRating;

    public KatRatingEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ratingId_generator")
    @SequenceGenerator(name="ratingId_generator", sequenceName = "KAT_RATING_SEQ", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DATE_RATING", nullable = false)
    public java.sql.Date getDateRating() {
        return dateRating;
    }

    public void setDateRating(java.sql.Date dateRating) {
        this.dateRating = dateRating;
    }

    @Basic
    @Column(name = "COMMENTS", nullable = true, length = 4000)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
    @JoinColumn(name = "ID_QUESTION", referencedColumnName = "ID", nullable = false)
    public KatQuestionEntity getKatQuestionByIdQuestion() {
        return katQuestionByIdQuestion;
    }

    public void setKatQuestionByIdQuestion(KatQuestionEntity katQuestionByIdQuestion) {
        this.katQuestionByIdQuestion = katQuestionByIdQuestion;
    }

    @ManyToOne
    @JoinColumn(name = "ID_TYPE_RATING", referencedColumnName = "ID", nullable = false)
    public KatTypeRatingEntity getKatTypeRatingByIdTypeRating() {
        return katTypeRatingByIdTypeRating;
    }

    public void setKatTypeRatingByIdTypeRating(KatTypeRatingEntity katTypeRatingByIdTypeRating) {
        this.katTypeRatingByIdTypeRating = katTypeRatingByIdTypeRating;
    }
}
