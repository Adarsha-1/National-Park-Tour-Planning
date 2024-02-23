package org.example.repo;

import org.example.entity.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeImageRepo extends JpaRepository<RecipeImage, Integer> {


    @Query("select r from RecipeImage r where r.recipeId.id = :recipeId")
    public RecipeImage findByRecipeId(@Param("recipeId") Integer recipeId);
}
