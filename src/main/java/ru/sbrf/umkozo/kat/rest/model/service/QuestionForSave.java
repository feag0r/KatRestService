package ru.sbrf.umkozo.kat.rest.model.service;

public class QuestionForSave {

    private String question;

    private int userId;

    private int subjectId;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getQuestion() {
        return question;
    }

    public int getUserId() {
        return userId;
    }

    public int getSubjectId() {
        return subjectId;
    }
}
