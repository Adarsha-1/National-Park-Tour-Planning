package org.example.service;

import org.example.entity.Recipe;
import org.example.entity.RecipeLikes;
import org.example.repo.RecipeLikesRepo;
import org.example.repo.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeLikesService {

    @Autowired
    private RecipeLikesRepo recipeLikesRepo;

    @Autowired
    private RecipeRepository recipeRepository;

    public void addRecipeLikes(Integer recipeId) {
        //Recipe recipe = recipeRepository.findByDishId(recipeId);
        RecipeLikes recipeLikes = recipeLikesRepo.findLikesByRecipe(recipeId);
        if (recipeLikes == null) {
            Recipe recipe = recipeRepository.findByDishId(recipeId);
            RecipeLikes recipeLikes1 = new RecipeLikes();
            recipeLikes1.setLikes(1);
            recipeLikes1.setRecipeId(recipe);
            recipeLikesRepo.save(recipeLikes1);
        } else {
            recipeLikes.setLikes(recipeLikes.getLikes() + 1);
            recipeLikesRepo.save(recipeLikes);
        }

    }

    public void removeRecipeLikes(Integer recipeId) {
        //Recipe recipe = recipeRepository.findByDishId(recipeId);
        RecipeLikes recipeLikes = recipeLikesRepo.findLikesByRecipe(recipeId);
        if (recipeLikes == null) {
            Recipe recipe = recipeRepository.findByDishId(recipeId);
            RecipeLikes recipeLikes1 = new RecipeLikes();
            recipeLikes1.setLikes(0);
            recipeLikes1.setRecipeId(recipe);
            recipeLikesRepo.save(recipeLikes1);
        } else {
            recipeLikes.setLikes(recipeLikes.getLikes() - 1);
            recipeLikesRepo.save(recipeLikes);
        }

    }
}
