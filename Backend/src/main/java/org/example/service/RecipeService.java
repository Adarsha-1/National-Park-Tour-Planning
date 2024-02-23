package org.example.service;


import org.example.entity.*;
import org.example.model.Comment;
import org.example.model.RecipeDetails;
import org.example.model.UserRecipeList;
import org.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.exception.RecipeNotFoundException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeImageRepo recipeImageRepo;

    @Autowired
    private RecipeCommentsRepo recipeCommentsRepo;

    @Autowired
    private RecipeLikesRepo recipeLikesRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRecipeRepo userRecipeRepo;

    public List<RecipeDetails> searchTheRecipeNames(String name) {
        Pageable pageable = PageRequest.of(0, 100);
        //List<Recipe> recipes = recipeRepository.findByName(name, pageable).getContent();
        List<Recipe> recipeList = recipeRepository.findRandomByName(name,pageable);
        List<RecipeDetails> recipeDetailsList = new ArrayList<>();
        for (int i=0;i<recipeList.size();i++) {
            RecipeImage recipeImage = recipeImageRepo.findByRecipeId(recipeList.get(i).getId());
            RecipeDetails recipeDetails = new RecipeDetails();
            recipeDetails.setId(recipeList.get(i).getId());
            if (recipeImage != null) {
                recipeDetails.setRecipeImage(recipeImage.getImageUrl());
            }
            recipeDetails.setName(recipeList.get(i).getName());
            recipeDetailsList.add(recipeDetails);

        }
        return recipeDetailsList;
    }

    public List<RecipeDetails> searchByRandom() {
        Pageable pageable = PageRequest.of(0, 20);
        //List<Recipe> recipes = recipeRepository.findByName(name, pageable).getContent();
        List<Recipe> recipeList = recipeRepository.findRandomRecipe(pageable);
        List<RecipeDetails> recipeDetailsList = new ArrayList<>();
        for (int i=0;i<recipeList.size();i++) {
            RecipeImage recipeImage = recipeImageRepo.findByRecipeId(recipeList.get(i).getId());
            RecipeDetails recipeDetails = new RecipeDetails();
            recipeDetails.setId(recipeList.get(i).getId());
            recipeDetails.setRecipeImage(recipeImage.getImageUrl());
            recipeDetails.setName(recipeList.get(i).getName());
            recipeDetailsList.add(recipeDetails);
        }
        return recipeDetailsList;
    }

    public List<RecipeDetails> searchByNoOfSteps(Integer stepsCount) {
        Pageable pageable = PageRequest.of(0, 100);
        List<Recipe> recipeList = recipeRepository.findByNoOfSteps(stepsCount, pageable);
        List<RecipeDetails> recipeDetailsList = new ArrayList<>();
        for (int i=0;i<recipeList.size();i++) {
            RecipeImage recipeImage = recipeImageRepo.findByRecipeId(recipeList.get(i).getId());
            RecipeDetails recipeDetails = new RecipeDetails();
            recipeDetails.setId(recipeList.get(i).getId());
            if (recipeImage != null){
                recipeDetails.setRecipeImage(recipeImage.getImageUrl());
            }
            recipeDetails.setName(recipeList.get(i).getName());
            recipeDetailsList.add(recipeDetails);

        }
        return recipeDetailsList;
    }

    public RecipeDetails searchById(Integer id) {
        Recipe recipe = recipeRepository.findByDishId(id);
        RecipeDetails recipeDetails = new RecipeDetails();
        if (recipe != null) {
            recipeDetails.setId(recipe.getId());
            recipeDetails.setName(recipe.getName());
            recipeDetails.setDescription(recipe.getDescription());
            recipeDetails.setNoOfIngredients(Integer.valueOf(recipe.getNoOfIngredients()));
            recipeDetails.setNoOfSteps(recipe.getNoOfSteps());
            recipeDetails.setTime(recipe.getTime());
            String a = recipe.getRecipeProcedure().replace("[", "").replace("]", "").replace(","," ");

            String[] instructionsList = a.split("  ");
            for (int i = 0; i < instructionsList.length; i++) {
                instructionsList[i] = instructionsList[i].replace("'", "");
            }

            //System.out.println(Arrays.toString(instructionsList));
            recipeDetails.setProcedure(Arrays.asList(instructionsList));
            //recipeDetails.setIngredients(Collections.singletonList(recipe.getIngredients().replace("[","").replace("]","").replace("'", "")));
            String ingredientList = recipe.getIngredients().replace("[", "").replace("]", "").replace(","," ");

            String[] ingredients = ingredientList.split("  ");
            for (int i = 0; i < ingredients.length; i++) {
                ingredients[i] = ingredients[i].replace("'", "");
            }
            recipeDetails.setIngredients(Arrays.asList(ingredients));
            RecipeImage recipeImage = recipeImageRepo.findByRecipeId(recipe.getId());
            if (recipeImage !=null) {
                recipeDetails.setRecipeImage(recipeImage.getImageUrl());
            }
            List<RecipeComments> recipeComments = recipeCommentsRepo.findCommentsByRecipeId(id);
            List<String> comments = new ArrayList<>();
            List<Comment> commentList = new ArrayList<>();
            for (int i=0;i<recipeComments.size();i++) {
                comments.add(recipeComments.get(i).getComments());
                Comment comment = new Comment();
                comment.setComment(recipeComments.get(i).getComments());
                comment.setUserName(recipeComments.get(i).getUserName());
                commentList.add(comment);
            }
            //recipeDetails.setComments(commentList);
            recipeDetails.setCommentList(commentList);
            recipeDetails.setNoOfLikes(getRecipeLikes(id));
        }
        return recipeDetails;
    }
    public List<RecipeDetails> getUnapprovedRecipes() {
        List<Recipe> recipeList = recipeRepository.findUnapprovedRecipes();
        List<RecipeDetails> recipeDetailsList = new ArrayList<>();
        for (int i=0;i<recipeList.size();i++) {
            RecipeImage recipeImage = recipeImageRepo.findByRecipeId(recipeList.get(i).getId());
            RecipeDetails recipeDetails = new RecipeDetails();
            recipeDetails.setId(recipeList.get(i).getId());
            if (recipeImage != null){
                recipeDetails.setRecipeImage(recipeImage.getImageUrl());
            }
            recipeDetails.setName(recipeList.get(i).getName());
            recipeDetailsList.add(recipeDetails);
        }
        return recipeDetailsList;
    }
    public void approveRecipe(Integer recipeId) {
        Recipe recipe = recipeRepository.findByDishId(recipeId);
        if (recipe != null) {
            recipe.setApproved(true);
            recipeRepository.save(recipe);
        } else {
            throw new RecipeNotFoundException("Recipe with id " + recipeId + " not found.");
        }
    }

    private Integer getRecipeLikes(Integer recipeId) {
        RecipeLikes recipeLikes = recipeLikesRepo.findLikesByRecipe(recipeId);
        if (recipeLikes == null) {
            return 0;
        }
        return recipeLikes.getLikes();
    }

    public UserRecipeList getUserAddedRecipes(String userName) {
        User user = userRepository.findByUserName(userName);
        List<UserRecipe> approvedList = new ArrayList<>();
        List<UserRecipe> pendingList = new ArrayList<>();
        UserRecipeList userRecipeList = new UserRecipeList();
        if (user != null) {
            approvedList = userRecipeRepo.findByUserIdAndApprovedTrue(user.getId());
            pendingList = userRecipeRepo.findByUserIdAndApprovedFalse(user.getId());
            userRecipeList.setApprovalList(convertRecipeToRecipeDetails(approvedList));
            userRecipeList.setPendingList(convertRecipeToRecipeDetails(pendingList));
        }
        return userRecipeList;
    }

    private List<RecipeDetails> convertRecipeToRecipeDetails(List<UserRecipe> recipeList) {
        List<RecipeDetails> recipeDetailsList = new ArrayList<>();
        for (int i=0;i<recipeList.size();i++) {
            RecipeImage recipeImage = recipeImageRepo.findByRecipeId(recipeList.get(i).getRecipeId().getId());
            RecipeDetails recipeDetails = new RecipeDetails();
            recipeDetails.setId(recipeList.get(i).getId());
            if (recipeImage != null){
                recipeDetails.setRecipeImage(recipeImage.getImageUrl());
            }
            recipeDetails.setName(recipeList.get(i).getRecipeId().getName());
            recipeDetails.setId(recipeList.get(i).getRecipeId().getId());
            recipeDetailsList.add(recipeDetails);
        }
        return recipeDetailsList;
    }

    public String addNewRecipe(Recipe recipe, String userName) {
        User user = userRepository.findByUserName(userName);
        //Recipe recipe1 = new Recipe();
        String ingredients = recipe.getIngredients();
        recipe.setNoOfIngredients(String.valueOf(ingredients.split(",").length));
        String steps = recipe.getRecipeProcedure();
        String r[] = steps.split("\n");
        recipe.setRecipeProcedure(String.join(", ", r));
        recipe.setApproved(false);
        Recipe recipe1 = new Recipe();
        recipe1.setApproved(false);
        recipe1.setRecipeProcedure(String.join(", ", r));
        recipe1.setName(recipe.getName());
        recipe1.setDescription(recipe.getDescription());
        recipe1.setIngredients(recipe.getIngredients());
        recipe1.setNoOfIngredients(String.valueOf(ingredients.split(",").length));
        recipe1.setTime(recipe.getTime());
        recipe1.setNoOfSteps(recipe.getNoOfSteps());
        recipeRepository.save(recipe1);
        UserRecipe userRecipe = new UserRecipe();
        userRecipe.setUserId(user);
        userRecipe.setRecipeId(recipe1);
        userRecipeRepo.save(userRecipe);
        return "success";
    }

}
