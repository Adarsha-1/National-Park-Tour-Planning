package org.example.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recipeComments", schema = "adt")
public class RecipeComments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "comments")
    private String comments;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "recipeId")
    private Recipe recipeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Recipe getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Recipe recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "RecipeComments{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", comments='" + comments + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
