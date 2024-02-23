package org.example.model;

public class CommentDetails {

    private String userName;

    private String comment;

    private Integer recipeId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "CommentDetails{" +
                "userName='" + userName + '\'' +
                ", comment='" + comment + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
