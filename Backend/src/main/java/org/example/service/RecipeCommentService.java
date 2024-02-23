package org.example.service;

import org.example.entity.Recipe;
import org.example.entity.RecipeComments;
import org.example.entity.User;
import org.example.repo.RecipeCommentsRepo;
import org.example.repo.RecipeRepository;
import org.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeCommentService {

    @Autowired
    private RecipeCommentsRepo recipeCommentsRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;


    public void addRecipeComments(Integer recipeId, String comment, String userName) {
        User user = new User();
        if (userName!=null){
            user = userRepository.findByUserName(userName.replace('"', ' ').replace(" ", ""));
        }
        RecipeComments recipeComments = new RecipeComments();
        if (userName == null || user.getId() == null) {
            recipeComments.setUserName("anonymous");
        } else {
            recipeComments.setUserName(userName);
        }
        recipeComments.setComments(comment);
        Recipe recipe = recipeRepository.findByDishId(recipeId);
        if (recipe != null) {
            recipeComments.setRecipeId(recipe);
        }
        recipeCommentsRepo.save(recipeComments);
    }
}
