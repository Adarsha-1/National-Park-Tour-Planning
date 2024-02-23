package org.example.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "recipeImage", schema = "adt")
public class RecipeImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "imageUrl")
    private String imageUrl;

    @OneToOne(targetEntity = Recipe.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "recipeId")
    private Recipe recipeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Recipe getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Recipe recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "RecipeImage{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
