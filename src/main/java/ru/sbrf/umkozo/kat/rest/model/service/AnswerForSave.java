package ru.sbrf.umkozo.kat.rest.model.service;

public class AnswerForSave {
    private String answer;
    private boolean isRight;
    private int questionId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(boolean right) {
        isRight = right;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
