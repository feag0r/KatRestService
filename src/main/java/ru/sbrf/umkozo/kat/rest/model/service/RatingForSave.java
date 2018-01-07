package ru.sbrf.umkozo.kat.rest.model.service;

public class RatingForSave {
    private String comments;
    private int userId;
    private int questionId;
    private int typeRatingId;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getTypeRatingId() {
        return typeRatingId;
    }

    public void setTypeRatingId(int typeRatingId) {
        this.typeRatingId = typeRatingId;
    }
}
