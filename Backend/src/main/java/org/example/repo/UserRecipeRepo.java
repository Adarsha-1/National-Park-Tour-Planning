package org.example.repo;

import org.example.entity.Recipe;
import org.example.entity.UserRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRecipeRepo extends JpaRepository<UserRecipe, Integer> {

    @Query("select ur from UserRecipe ur where ur.userId.id = :userId and ur.recipeId.approved = true")
    public List<UserRecipe> findByUserIdAndApprovedTrue(@Param("userId") Integer userId);

    @Query("select ur from UserRecipe ur where ur.userId.id = :userId and ur.recipeId.approved = false")
    public List<UserRecipe> findByUserIdAndApprovedFalse(@Param("userId") Integer userId);

    @Query("select u from UserRecipe u where u.recipeId.id = :recipeId")
    public UserRecipe findByRecipeId(@Param("recipeId") Integer recipeId);
}
