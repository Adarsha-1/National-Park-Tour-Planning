package org.example.controller;

import org.example.entity.Recipe;
import org.example.model.CommentDetails;
import org.example.model.RecipeDetails;
import org.example.service.RecipeCommentService;
import org.example.service.RecipeLikesService;
import org.example.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/recipe")
@CrossOrigin(origins="*")
public class RecipeController {
    @Autowired
    private final RecipeService recipeService;

    @Autowired
    private final RecipeCommentService recipeCommentService;

    @Autowired
    private final RecipeLikesService recipeLikesService;


    public RecipeController(RecipeService recipeService, RecipeCommentService recipeCommentService, RecipeLikesService recipeLikesService) {
        this.recipeService = recipeService;
        this.recipeCommentService = recipeCommentService;
        this.recipeLikesService = recipeLikesService;
    }

    @GetMapping("/unapproved")
    public ResponseEntity<List<RecipeDetails>> getUnapprovedRecipes() {
        List<RecipeDetails> recipeDetailsList = recipeService.getUnapprovedRecipes();
        return new ResponseEntity<>(recipeDetailsList, HttpStatus.OK);
    }

    @PutMapping("/approve")
    public ResponseEntity<Void> approveRecipe(@RequestParam String recipeId) {
        recipeService.approveRecipe(Integer.valueOf(recipeId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<RecipeDetails>> searchByName(@RequestParam String name) {
        List<RecipeDetails> recipeList = recipeService.searchTheRecipeNames(name);
        return new ResponseEntity<>(recipeList, HttpStatus.OK);
    }

    @GetMapping(value = "/search/random")
    public ResponseEntity<List<RecipeDetails>> searchByRandom() {
        List<RecipeDetails> recipeList = recipeService.searchByRandom();
        return new ResponseEntity<>(recipeList, HttpStatus.OK);
    }

    @GetMapping(value = "/search/steps")
    public ResponseEntity<List<RecipeDetails>> searchBySteps(@RequestParam Integer stepsCount) {
        List<RecipeDetails> recipeList = recipeService.searchByNoOfSteps(stepsCount);
        return new ResponseEntity<>(recipeList, HttpStatus.OK);
    }

    @GetMapping(value = "/dish")
    public ResponseEntity<RecipeDetails> searchById(@RequestParam("id") Integer id) {
        RecipeDetails recipe = recipeService.searchById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping(value = "/addComments")
    public ResponseEntity<Void> addRecipeComments(@RequestBody CommentDetails commentDetails) {
        recipeCommentService.addRecipeComments(commentDetails.getRecipeId(), commentDetails.getComment(), commentDetails.getUserName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/addLike")
    public ResponseEntity<Void> addRecipeLike(@RequestBody RecipeDetails recipeDetails) {
        recipeLikesService.addRecipeLikes(recipeDetails.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/removeLike")
    public ResponseEntity<Void> removeRecipeLike(@RequestBody RecipeDetails recipeDetails) {
        recipeLikesService.removeRecipeLikes(recipeDetails.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/addRecipe")
    public ResponseEntity<String> addNewRecipe(@RequestBody Recipe recipe, @RequestParam("userName") String userName) {
        return new ResponseEntity<>(recipeService.addNewRecipe(recipe, userName), HttpStatus.OK);
    }


}
