package ru.sbrf.umkozo.kat.rest.model.db;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "KAT_ANSWER", schema = "C##KAT")
public class KatAnswerEntity {
    private int id;
    private String answer;
    private boolean isRight;
    private KatQuestionEntity katQuestionByIdQuestion;

    public KatAnswerEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answerId_generator")
    @SequenceGenerator(name="answerId_generator", sequenceName = "KAT_ANSWER_SEQ", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ANSWER", nullable = false, length = 4000)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "ISRIGHT", nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }

    @ManyToOne
    @JoinColumn(name = "ID_QUESTION", referencedColumnName = "ID", nullable = false)
    public KatQuestionEntity getKatQuestionByIdQuestion() {
        return katQuestionByIdQuestion;
    }

    public void setKatQuestionByIdQuestion(KatQuestionEntity katQuestionByIdQuestion) {
        this.katQuestionByIdQuestion = katQuestionByIdQuestion;
    }
}
