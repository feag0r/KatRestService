package ru.sbrf.umkozo.kat.rest.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "KAT_TYPE_RATING", schema = "C##KAT")
public class KatTypeRatingEntity {
    private int id;
    private String ratingName;
    private int score;
    private Collection<KatRatingEntity> katRatingsById;

    public KatTypeRatingEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typeId_generator")
    @SequenceGenerator(name="typeId_generator", sequenceName = "KAT_TYPE_RATING_SEQ", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "RATING_NAME", nullable = false, length = 4000)
    public String getRatingName() {
        return ratingName;
    }

    public void setRatingName(String ratingName) {
        this.ratingName = ratingName;
    }

    @Basic
    @Column(name = "SCORE", nullable = false, precision = 0)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @OneToMany(mappedBy = "katTypeRatingByIdTypeRating")
    @JsonIgnore
    public Collection<KatRatingEntity> getKatRatingsById() {
        return katRatingsById;
    }

    public void setKatRatingsById(Collection<KatRatingEntity> katRatingsById) {
        this.katRatingsById = katRatingsById;
    }
}