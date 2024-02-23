import React, { useState, useEffect } from "react";

import "bootstrap/dist/css/bootstrap.min.css";
import {Col, Row} from "react-bootstrap";

import SearchResult from "../../components/searchResult/SearchResult";
import axios from "axios";
import "./search.css";

const inputReset = (id) => {

  document.getElementById(id).value = "";

};

const selectReset = (id) => {

  document.getElementById(id).selectedIndex = 0;

};



const SearchForm = () => {

  // const [state, dispatch] = useRecipeContext();

  // const { categories, areas, category, area, ingredient, mealName } = state;


  const [categoryList, setCategoryList] = useState([]);
  const [areaList, setAreaList] = useState([]);

  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedArea, setSelectedArea] = useState("");
  const [searchIngredient, setSearchIngredient] = useState("");
  const [searchMealName, setSearchMealName] = useState("");
  const [selectedIngredient, setSelectedIngredient] = useState("");
  const [selectedMealName, setSelectedMealName] = useState("");
  const [randomResult, setRandomResult] = useState()
  const handleSelectChange = (event) => {
    if (event.target.name === "area") {   
      inputReset("mealName");  
      inputReset("ingredient");  
      selectReset("category");
     
    } else if (event.target.name === "ingredient") {
      setSearchIngredient(event.target.value);
      setSearchMealName("")
     
     
    } else if (event.target.name === "mealName") {
      setSearchMealName(event.target.value);
      setSearchIngredient("")
         
    }
  };

  const handleFormSubmit = (event) => {
    event.preventDefault();

    console.log("Event: ", event.target.name);
    // if (!selectedCategory && !selectedArea) {
    //     return false;
    // }
    if (event.target.name === "ingredient") {
      setSelectedIngredient(searchIngredient);
      setSelectedMealName("");
      inputReset("mealName");
      // console.log(searchIngredient);
    } else if (event.target.name === "mealName") {
      setSelectedMealName(searchMealName);
      setSelectedIngredient("");
      inputReset("ingredient"); 
      // console.log("selectedMealName",selectedMealName);
    }

    // console.log("selectedIngredient: ",selectedIngredient);
    setSelectedCategory("");
    setSelectedArea("");    
    
  };

  useEffect(() => {
    const getCategory = async () => {
      try {
        console.log("entered")
        const response = await axios.get("https://platemate.herokuapp.com/playmate/recipe/search/random");
                
        //const response = await searchRecipes(query);

        if (!response.ok) {
          throw new Error("something went wrong!");
        }

        const { meals } = await response.json();
        // console.log(meals);

        // if (query === "list.php?c=list") {
        //   setCategoryList(meals);
        // } else {
        //   setAreaList(meals);
        // }
      } catch (err) {
        console.error(JSON.parse(JSON.stringify(err)));
      }
    };

    //getCategory();
  }, []);
  let item = {};

  return (
    <div className="container-fluid">
      <div className="search-container">
        <div className="search-small-screen">
          <input
            className="searchbar"
            type="text"
            name="mealName"
            placeholder="Search Recipe"
            onChange={handleSelectChange}
            id="mealName"
          />
          <button
            className="searchbar-btn"
            type="submit"
            name="mealName"
            onClick={handleFormSubmit}
          >
            Search
          </button>
        </div>
        <div>
          <input
            type="text"
            name="ingredient"
            className="searchbar"
            placeholder="number of steps"
            onChange={handleSelectChange}            
            id="ingredient"
          />
          <button
            type="submit"
            className="searchbar-btn"
            name="ingredient"
            onClick={handleFormSubmit}
          >
            Search
          </button>
        </div>
        <div className="filters">
          
        </div>
      </div>
      <div>
        <SearchResult
          mealName={selectedMealName}
          steps = {selectedIngredient}
          key = {item}
        />
      </div>
    </div>
  );
};

export default SearchForm;
