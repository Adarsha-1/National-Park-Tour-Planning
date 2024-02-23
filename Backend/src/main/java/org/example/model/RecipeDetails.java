package org.example.model;

import java.util.List;

public class RecipeDetails {

    private Integer id;

    private String name;

    private String description;

    private  Integer noOfSteps;

    private Integer noOfIngredients;

    private List<String> ingredients;

    private List<String> procedure;

    private String recipeImage;

    private List<String> comments;

    private List<Comment> commentList;

    private Integer noOfLikes;

    private Integer time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfSteps() {
        return noOfSteps;
    }

    public void setNoOfSteps(Integer noOfSteps) {
        this.noOfSteps = noOfSteps;
    }

    public Integer getNoOfIngredients() {
        return noOfIngredients;
    }

    public void setNoOfIngredients(Integer noOfIngredients) {
        this.noOfIngredients = noOfIngredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getProcedure() {
        return procedure;
    }

    public void setProcedure(List<String> procedure) {
        this.procedure = procedure;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public Integer getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(Integer noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "RecipeDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", noOfSteps=" + noOfSteps +
                ", noOfIngredients=" + noOfIngredients +
                ", ingredients=" + ingredients +
                ", procedure=" + procedure +
                ", recipeImage='" + recipeImage + '\'' +
                ", comments=" + comments +
                ", commentList=" + commentList +
                ", noOfLikes=" + noOfLikes +
                ", time=" + time +
                '}';
    }
}
