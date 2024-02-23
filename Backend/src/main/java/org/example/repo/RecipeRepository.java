package org.example.repo;

import org.example.entity.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {


    @Query("select r from Recipe r where r.name like %:name%")
    public List<Recipe> findByName(@Param("name") String name);

    @Query("SELECT r FROM Recipe r WHERE r.name LIKE %:name% AND r.approved = TRUE ORDER BY RANDOM()")
    public List<Recipe> findRandomByName(@Param("name") String name, Pageable pageable);



    /*@Query(value = "SELECT r FROM Recipe r WHERE r.name LIKE %:name% ORDER BY RAND() LIMIT 100", nativeQuery = true)
    public List<Recipe> findByName(@Param("name") String name);*/

    @Query("select r from Recipe r where r.noOfSteps = :steps AND r.approved = TRUE ORDER BY RANDOM() ")
    public List<Recipe> findByNoOfSteps(@Param("steps") Integer steps, Pageable pageable);

    @Query("select r from Recipe r where r.id = :id")
    public Recipe findByDishId(@Param("id") Integer id);


    @Query("select r from Recipe r where r.approved = false")
    public List<Recipe> findUnapprovedRecipes();

    @Query("select r from Recipe r where r.id > 0 and r.id < 21")
    public List<Recipe> findRandomRecipe(Pageable pageable);

    @Query("select ur.recipeId from UserRecipe ur where ur.userId.id = :userId and ur.recipeId.approved = true")
    public List<Recipe> findByUserIdAndApprovedTrue(@Param("userId") Integer userId);

    @Query("select ur.recipeId from UserRecipe ur where ur.userId.id = :userId and ur.recipeId.approved = false")
    public List<Recipe> findByUserIdAndApprovedFalse(@Param("userId") Integer userId);


}
