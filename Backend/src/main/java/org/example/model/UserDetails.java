package org.example.model;

public class UserDetails {

    private String userName;

    private Integer recipeId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userName='" + userName + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
