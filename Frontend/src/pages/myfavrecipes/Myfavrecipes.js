import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { Col, Row } from "react-bootstrap";
import staticPng from "../../assets/static.png"

const Myfavrecipes = () => {

  const [recipeList, setRecipeList] = useState([]);
  const [user, setUser] = useState();

  useEffect(() => {
    const a = localStorage.getItem("user")
    setUser(a)
    console.log("username is: ", user)
    const getFavoriteRecipes = async () => {
      let url = "https://platemate.herokuapp.com/playmate/user/favoriteRecipes?userName=" + a
      try {
        const response = await axios.get(url);
        setRecipeList(response.data);
      } catch (err) {
        console.error(JSON.parse(JSON.stringify(err)));
      }
    };
    getFavoriteRecipes();
  }, []);

  return (
    <div className="recipe-results-container">
      <h2>My Favorite Recipes</h2>
      {recipeList.map((recipe) => (
        <Col className="recipe-card" lg={3} key={recipe.id}>
          <Link to={`/recipe/${recipe.id}`}>
            <div key={recipe.id}>
              <Row className="recipe-title">
                <h4>{recipe.name}</h4>
              </Row>
              <Row>
                <img src={recipe.recipeImage ? recipe.recipeImage : staticPng} alt={recipe.name} />
              </Row>
            </div>
          </Link>
        </Col>
      ))}
    </div>
  );
};

export default Myfavrecipes;
