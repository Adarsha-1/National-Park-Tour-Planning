package org.example.repo;

import org.example.entity.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteRepo extends JpaRepository<UserFavorite, Integer> {

    @Query("select u from UserFavorite u where u.userId.id = :userId")
    public List<UserFavorite> findFavoriteByUserId(@Param("userId") Integer userId);

    @Query("select u from UserFavorite u where u.userId.id = :userId and u.recipeId.id = :recipeId")
    public UserFavorite findFavoriteByUserAndRecipeId(@Param("userId") Integer userId, @Param("recipeId") Integer recipeId);
}
