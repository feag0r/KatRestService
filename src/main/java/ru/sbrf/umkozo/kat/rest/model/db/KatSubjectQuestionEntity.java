package ru.sbrf.umkozo.kat.rest.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "KAT_SUBJECT_QUESTION", schema = "C##KAT")
public class KatSubjectQuestionEntity {
    private int id;
    private String subject;
    private String description;
    private Collection<KatQuestionEntity> katQuestionsById;

    public KatSubjectQuestionEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectId_generator")
    @SequenceGenerator(name="subjectId_generator", sequenceName = "KAT_SUBJECT_QUESTION_SEQ", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SUBJECT", nullable = false, length = 4000)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 4000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "katSubjectQuestionByIdSubject")
    @JsonIgnore
    public Collection<KatQuestionEntity> getKatQuestionsById() {
        return katQuestionsById;
    }

    public void setKatQuestionsById(Collection<KatQuestionEntity> katQuestionsById) {
        this.katQuestionsById = katQuestionsById;
    }
}