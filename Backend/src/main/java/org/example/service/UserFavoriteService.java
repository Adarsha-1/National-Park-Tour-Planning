package org.example.service;

import org.example.entity.*;
import org.example.model.RecipeDetails;
import org.example.model.UserDetails;
import org.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFavoriteService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserFavoriteRepo userFavoriteRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeLikesRepo recipeLikesRepo;

    @Autowired
    private UserRecipeRepo userRecipeRepo;

    @Autowired
    private RecipeImageRepo recipeImageRepo;

    public void addUserFavoriteRecipe(Integer recipeId, String userName) {
        UserRecipe userRecipe = userRecipeRepo.findByRecipeId(recipeId);
        if (userRecipe == null) {
            Recipe recipe = recipeRepository.findByDishId(recipeId);
            User user = userRepository.findByUserName(userName);
            UserFavorite userFavorite = new UserFavorite();
            userFavorite.setRecipeId(recipe);
            userFavorite.setUserId(user);
            userFavoriteRepo.save(userFavorite);
        }

    }

    public String removeUserFavoriteRecipe(Integer recipeId, String userName) {
        User user = userRepository.findByUserName(userName);
        UserFavorite userFavorite = userFavoriteRepo.findFavoriteByUserAndRecipeId(user.getId(), recipeId);
        userFavoriteRepo.delete(userFavorite);
        return "success";
    }

    public List<RecipeDetails> getUserFavoriteRecipes(String userName) {
        User user = userRepository.findByUserName(userName);
        List<UserFavorite> userFavorites = userFavoriteRepo.findFavoriteByUserId(user.getId());
        List<Recipe> userFavoriteRecipes = new ArrayList<>();
        for (UserFavorite userFavorite : userFavorites) {
            Recipe recipe = userFavorite.getRecipeId();
            userFavoriteRecipes.add(recipe);
        }

        List<RecipeDetails> recipeDetailsList = new ArrayList<>();
        for (Recipe recipe : userFavoriteRecipes) {
            RecipeDetails recipeDetails = new RecipeDetails();
            recipeDetails.setId(recipe.getId());
            recipeDetails.setName(recipe.getName());

            RecipeImage recipeImage = recipeImageRepo.findByRecipeId(recipe.getId());
            if (recipeImage != null) {
                recipeDetails.setRecipeImage(recipeImage.getImageUrl());
            }
            recipeDetailsList.add(recipeDetails);
        }
        return recipeDetailsList;
    }
}
