package org.example.repo;

import org.example.entity.RecipeComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeCommentsRepo extends JpaRepository<RecipeComments, Integer> {


    @Query("select r from RecipeComments r where r.recipeId.id = :recipeId")
    public List<RecipeComments> findCommentsByRecipeId(@Param("recipeId") Integer recipeId);
}
