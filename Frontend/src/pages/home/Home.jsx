  import React, { useState } from "react";
import { Container, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";
// import RecipeSearch from "../extras/RecipeSearch";
import "./home.css";
import { homeArt } from "../../assets";
import Header from '../../components/Header/Header'
import * as Icon from "react-bootstrap-icons";

const Main = () => {
  console.log('home: /')
  return (
    
   
    <Col>
        <Col className="home-banner banner-container">
          <h2>Find New Recipes to Try!</h2>
          <p>Gain access to delicious recipes and join now!</p>
          <img src={homeArt} alt="palceholder" />
          <button id="get-started-btn">
            <Link to="/search">Find a Recipe</Link>
          </button>
        </Col>
        <Col className="home-description">
        <h3>What you can do...</h3>
        <Row className="description-item-container">
          <Col className="description-item">
            <h4>Search for Recipes</h4>
            <Icon.Search
              style={{ width: "100px", height: "150px", color: "grey" }}
            />
            {/* <img src="https://via.placeholder.com/125" alt="palceholder" /> */}
            <p>
              Enter an ingredient, dish name or use filters to find receipes.
            </p>
          </Col>
          <Col className="description-item">
            <h4>Save recipes</h4>
            <Icon.Heart
              style={{ width: "100px", height: "150px", color: "#C05A5A" }}
            />
            {/* <img src="https://via.placeholder.com/125 " alt="palceholder" /> */}
            <p>
              Once you are logged in, you can bookmark recipes to your profile
              and add the recipe ingredients to your shopping cart!
            </p>
          </Col>
          <Col className="description-item">
            <h4>Explore!</h4>
            <Icon.GlobeAmericas
              style={{ width: "100px", height: "150px", color: "#578D3E" }}
            />
            <p>Enjoy and get a taste of different cuisines!</p>
          </Col>
        </Row>
      </Col>
    </Col>
  );
};

export default Main;