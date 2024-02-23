package org.example.repo;

import org.example.entity.RecipeLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeLikesRepo extends JpaRepository<RecipeLikes, Integer> {

    @Query("select r from RecipeLikes r where r.recipeId.id = :recipeId")
    public RecipeLikes findLikesByRecipe(@Param("recipeId") Integer recipeId);
}
