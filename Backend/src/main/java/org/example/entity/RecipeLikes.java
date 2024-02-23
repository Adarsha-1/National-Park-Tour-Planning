package org.example.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recipeLikes", schema = "adt")
public class RecipeLikes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "likes")
    private Integer likes;

    @OneToOne(targetEntity = Recipe.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "recipeId")
    private Recipe recipeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Recipe getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Recipe recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "RecipeLikes{" +
                "id=" + id +
                ", likes=" + likes +
                ", recipeId=" + recipeId +
                '}';
    }
}
