import React, { useState, useEffect } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import './searchResult.css'
import { Col, Row } from "react-bootstrap";
import staticPng from "../../assets/static.png"

const SearchResult = ({mealName, steps}) => {

    const [recipeList, setRecipeList] = useState([]);
    const [result, setResult] = useState('random');
    const meals = []

    useEffect(() => {
        console.log("entered search result")
        const getRecipe = async (query) => {
            try {
                console.log("query is:", query)
                let url = "https://platemate.herokuapp.com/playmate/recipe/search?name=" + query
                const response = await axios.get(url);
                console.log(response.ok);
                //const response = await searchRecipes(query);

                

                //const { meals } = await response.json();
                //const meals = response
                const  totalItems  = response.data.length;
                console.log("items are: ",totalItems )
                console.log("meals", meals);

                for (let val in response.data) {
                    meals.push(response.data[val]);
                  }

                  console.log("meals", meals);
                
                if(meals !== null)
                    setRecipeList(meals);
                else
                    setRecipeList([]);

            } catch (err) {
                console.error(JSON.parse(JSON.stringify(err)));
            }
        };

        const getRecipeBySteps = async (query) => {
          try {
              console.log("query is:", query)
              let url = "https://platemate.herokuapp.com/playmate/recipe/search/steps?stepsCount=" + query
              const response = await axios.get(url);
              console.log(response.ok);
              //const response = await searchRecipes(query);

              

              //const { meals } = await response.json();
              //const meals = response
              const  totalItems  = response.data.length;
              console.log("items are: ",totalItems )
              console.log("meals", meals);

              for (let val in response.data) {
                  meals.push(response.data[val]);
                }

                console.log("meals", meals);
              
              if(meals !== null)
                  setRecipeList(meals);
              else
                  setRecipeList([]);

          } catch (err) {
              console.error(JSON.parse(JSON.stringify(err)));
          }
      };

      const getRandom = async () => {
        try {
            let url = "https://platemate.herokuapp.com/playmate/recipe/search/random"
            const response = await axios.get(url);
            console.log(response.ok);
            //const response = await searchRecipes(query);

            

            //const { meals } = await response.json();
            //const meals = response
            const  totalItems  = response.data.length;
            console.log("items are: ",totalItems )
            console.log("meals", meals);

            for (let val in response.data) {
                meals.push(response.data[val]);
              }

              console.log("meals", meals);
            
            if(meals !== null)
                setRecipeList(meals);
            else
                setRecipeList([]);

        } catch (err) {
            console.error(JSON.parse(JSON.stringify(err)));
        }
    };

        
            if(mealName != '') {
                getRecipe(`${mealName}`);
                setResult(mealName);
            } else if(steps != '') {
              getRecipeBySteps(`${steps}`)
              setResult(steps)
            } else {
              getRandom()
              setResult()
            }
            
        

    }, [mealName]);


    return (
      <div className="recipe-results-container">
        <p id="search-req">Showing results for "{result}"</p>
          {recipeList.map((recipe) => (
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
            </Col>
          ))}
      </div>
    );
};

export default SearchResult;