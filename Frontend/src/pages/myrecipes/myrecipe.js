import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { Col, Row } from "react-bootstrap";
import './myRecipe.css';
import staticPng from "../../assets/static.png"

const MyRecipes = () => {

  const [approvedRecipes, setApprovedRecipes] = useState([]);
  const [waitingRecipes, setWaitingRecipes] = useState([]);
  const [user, setUser] = useState();

  useEffect(() => {
    const a = localStorage.getItem("user")
    setUser(a)
    console.log("username is: ", user)
    const getAddedRecipes = async () => {
      try {
        let url = "http://3.18.110.132:8086/playmate/user/addedRecipes?userName=" + a
        const response = await axios.get(url);
        console.log(response.ok);
        const  totalItems  = response.data.length;
              console.log("items are: ",totalItems );
        console.log("pending approvals are: ", response.data.pendingList)
        setApprovedRecipes(response.data.approvalList);
        setWaitingRecipes(response.data.pendingList);
      } catch (err) {
        console.error(JSON.parse(JSON.stringify(err)));
      }
    };
    getAddedRecipes();
    
  }, []);

  return (
    <div className="recipe-results-wrapper">
  <div className="recipe-results-container">
  <h1 style={{ textAlign: 'center' }}>Approved Recipes</h1>
      {approvedRecipes.length > 0 ? (approvedRecipes.map((recipe) => (
        <Col className="recipe-card" lg={3} key={recipe.id}>
          <Link to={`/recipe/${recipe.id}`}>
            <div>
              <Row className="recipe-title">
                <h4>{recipe.name}</h4>
              </Row>
              <Row>
                <img src={recipe.recipeImage ? recipe.recipeImage : staticPng} alt={recipe.name} />
              </Row>
            </div>
          </Link>
        </Col>
      ))) : <p className="recipe-results-container">None</p>}
  </div>

  <div className="recipe-results-container">
    <h1 style={{ textAlign: 'center' }}>Approval Pending Recipe</h1>
      {waitingRecipes.map((recipe) => (
        <Col className="recipe-card" lg={3} key={recipe.id}>
          <Link to={`/recipe/${recipe.id}`}>
            <div>
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
</div>
  //   <div>
  //     <div className="recipe-results-container">
  //   <h1 style={{ textAlign: 'center' }}>My Recipes</h1>
  //     {waitingRecipes.map((recipe) => (
  //       <Col className="recipe-card" lg={3}>
  //         <Link to={`/recipe/${recipe.id}`} key={recipe.id}>
  //           <div key={recipe.id}>
  //           <Row className="recipe-title">
  //               <h4>{recipe.name}</h4>
  //             </Row>
  //             <Row>
  //               <img src={recipe.recipeImage ? recipe.recipeImage : staticPng} alt={recipe.name} />
  //             </Row>
  //           </div>
  //         </Link>
  //       </Col>
  //     ))}
  // </div>

  // <div className="recipe-results-container1">
  //   {/* <h1 style={{ textAlign: 'center' }}>Approval Recipe</h1> */}
  //     {waitingRecipes.map((recipe) => (
  //       <Col className="recipe-card" lg={3}>
  //         <Link to={`/recipe/${recipe.id}`} key={recipe.id}>
  //           <div key={recipe.id}>
  //           <Row className="recipe-title">
  //               <h4>{recipe.name}</h4>
  //             </Row>
  //             <Row>
  //               <img src={recipe.recipeImage ? recipe.recipeImage : staticPng} alt={recipe.name} />
  //             </Row>
  //           </div>
  //         </Link>
  //       </Col>
  //     ))}
  // </div>
  //   </div>
    
    // <div className='recipe' style={{backgroundColor: "#fff7f4"}}> 
    //    <h1 style={{ textAlign: 'center' }}>My Recipes</h1>
      
    //   <div className="recipe-results-container">
    //   <h2 style={{backgroundColor:"#FFE5B4" }}>Waiting for Approval Recipes</h2>
    //     {waitingRecipes.map((recipe) => (
    //       <Col className="recipe-card" lg={3} key={recipe.id}>
    //         <Link to={`/recipe/${recipe.id}`}>
    //           <div key={recipe.id}>
    //             <Row className="recipe-title">
    //               <h4>{recipe.name}</h4>
    //             </Row>
    //             <Row>
    //               <img src={recipe.recipeImage} alt={recipe.name} />
    //             </Row>
    //           </div>
    //         </Link>
    //       </Col>
    //     ))}
    //   </div>
    //   {/* <div className="recipe-results-container">
    //   <h2 style={{backgroundColor:"#d4fbc2"}}>Approved Recipes</h2>
      
    //     {approvedRecipes.map((recipe) => (
    //       <Col className="recipe-card" lg={3} key={recipe.id}>
    //         <Link to={`/recipe/${recipe.id}`}>
    //           <div key={recipe.id}>
    //             <Row className="recipe-title">
    //               <h4>{recipe.name}</h4>
    //             </Row>
    //             <Row>
    //               <img src={recipe.recipeImage} alt={recipe.name} />
    //             </Row>
    //           </div>
    //         </Link>
    //       </Col>
    //     ))}
    //   </div> */}
    // </div>
  );
};

export default MyRecipes;
