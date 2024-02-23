package org.example.controller;

import org.example.entity.Recipe;
import org.example.entity.User;
import org.example.model.RecipeDetails;
import org.example.model.UserDetails;
import org.example.model.UserRecipeList;
import org.example.service.RecipeService;
import org.example.service.UserFavoriteService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFavoriteService userFavoriteService;

    @Autowired
    private RecipeService recipeService;


    @PostMapping(value = "/register")
    public ResponseEntity<String> addUser(@RequestBody User user) throws Exception{

        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);

    }

    @PostMapping(value = "/login")
    public ResponseEntity<User> login(@RequestBody User user) throws Exception {
        User user1 = userService.login(user);
        return new ResponseEntity<>(user1, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/addFavorite")
    public ResponseEntity<Void> addRecipeToFavorite(@RequestBody UserDetails userDetails) {
        userFavoriteService.addUserFavoriteRecipe(userDetails.getRecipeId(), userDetails.getUserName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/favoriteRecipes")
    public ResponseEntity<List<RecipeDetails>> getUserFavoriteRecipes(@RequestParam String userName) {
        List<RecipeDetails> recipeDetails  = userFavoriteService.getUserFavoriteRecipes(userName);
        return new ResponseEntity<>(recipeDetails ,HttpStatus.OK);
    }


    @GetMapping("/addedRecipes")
    public ResponseEntity<UserRecipeList> userAddedRecipes(@RequestParam("userName") String userName) {
        UserRecipeList userRecipeList = recipeService.getUserAddedRecipes(userName);
        return new ResponseEntity<>(userRecipeList, HttpStatus.OK);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFavoriteRecipe(@RequestBody UserDetails userDetails) {
    return new ResponseEntity<>(userFavoriteService.removeUserFavoriteRecipe(userDetails.getRecipeId(), userDetails.getUserName()), HttpStatus.OK);
    }

}
