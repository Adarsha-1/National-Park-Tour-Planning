package org.example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "userRecipe", schema = "adt")
public class UserRecipe  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userId")
    private User userId;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "recipeId")
    private Recipe recipeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Recipe getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Recipe recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "UserRecipe{" +
                "id=" + id +
                ", userId=" + userId +
                ", recipeId=" + recipeId +
                '}';
    }
}
