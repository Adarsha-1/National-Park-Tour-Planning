import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Admin.css';
import { Col, Row } from "react-bootstrap";
import staticPng from "../../assets/static.png";
import { Link } from 'react-router-dom';

function Admin() {
  const [recipes, setRecipes] = useState([]);
  const [length, setLength] = useState();

  useEffect(() => {
    async function fetchRecipes() {
      try {
        const result = await axios.get('https://platemate.herokuapp.com/playmate/recipe/unapproved');
        setRecipes(result.data);
        setLength(result.data.length)
      } catch(err) {
        console.error(JSON.parse(JSON.stringify(err)));
      }
      
      //setRecipes(result.data);
    }
    fetchRecipes();
  }, []);

  async function approveRecipe(recipeId) {
    try {
      var url = 'https://platemate.herokuapp.com/playmate/recipe/approve?recipeId=' + recipeId 
      const result = await axios.put(url);
      setRecipes(recipes.filter(recipe => recipe.id !== recipeId));
      setLength(result.length)
    } catch(err) {
      console.error(JSON.parse(JSON.stringify(err)));
    }
    //await axios.put(`/api/recipes/${recipeId}/approve`);
    //setRecipes(recipes.filter(recipe => recipe.id !== recipeId));
  }

  return (
    
    <div className="recipe-results-container">
    {
        length > 0 ? <h4 id="search-req"> Waiting for approval</h4> : <h6 id="search-req">No Pending approval</h6>
      }
      {recipes.map((recipe) => (
        <Col className="recipe-card" lg={3}>
          <Link to={`/recipe/${recipe.id}`} key={recipe.id}>
            <div key={recipe.id}>
            <Row className="recipe-title">
                <h4>{recipe.name}</h4>
              </Row>
              <Row>
                <img src={recipe.recipeImage ? recipe.recipeImage : staticPng} alt={recipe.name} />
              </Row>
              
            </div>
            
          </Link>
          <button onClick={() => approveRecipe(recipe.id)}>Approve</button>
        </Col>
      ))}
      &nbsp;
  </div>
  );
}

export default Admin;