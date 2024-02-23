import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useLocation, useNavigate } from "react-router-dom";
import "./recipeData.css";
import axios from "axios";
import { FaThumbsUp } from "react-icons/fa";
import CommentCard from "../../components/comments/CommentCard";
import StarIcon from "@mui/icons-material/Star";
import StarBorderOutlinedIcon from "@mui/icons-material/StarBorderOutlined";
import { Button } from "@material-ui/core";
import staticPng from "../../assets/static.png"


const RecipeData = () => {
  const [savedMealIds, setSavedMealIds] = useState();
  const [savedMeals, setSavedMeals] = useState([]);
  const [shoppingList, setShoppingList] = useState([]);
  const [authenticated, setAuthenticated] = useState(false)

  const [favourite, setFavourite] = useState(false);
  const [activeFavorite, setActiveFavorite] = useState(false);
  const [likedFavorite, setLikedFavorite] = useState(false);

  const handleFavourite = async() => {
    console.log("favorite before is: ", favourite)
    if (activeFavorite) {
      setActiveFavorite(false);
      try {
        const userName = localStorage.getItem("user")
        const userDetails = {
          recipeId: id,
          userName: userName
        }
        let url = "https://platemate.herokuapp.com/playmate/user/remove"
        const response = await axios.post(url, userDetails)

      } catch(err) {
        console.error(err);
      }
    } else {
      setActiveFavorite(true);
      try {
        const userName = localStorage.getItem("user")
        const userDetails = {
          recipeId: id,
          userName: userName
        }
        let url = "https://platemate.herokuapp.com/playmate/user/addFavorite"
        const response = await axios.post(url, userDetails)

      } catch(err) {
        console.error(err);
      }
    }
    setLikedFavorite(!likedFavorite)
    setFavourite(!favourite)
    




    //setFavourite(!favourite);
    console.log("favorite is: ", favourite)
    // if (favourite === true) {
    //   console.log("entered")
    //   try {
    //     const userName = localStorage.getItem("user")
    //     const userDetails = {
    //       recipeId: id,
    //       userName: userName
    //     }
    //     let url = "http://localhost:8086/playmate/user/addFavorite"
    //     const response = await axios.post(url, userDetails)

    //   } catch(err) {
    //     console.error(err);
    //   }
    // }
  };

  //const [state, dispatch] = useRecipeContext();

  const location = useLocation();
  const navigate = useNavigate();

  const { id } = useParams();

  const commentId = [];

  const meals = [];

  const [mealDetails, setMealDetails] = useState({
    id: undefined,
    name: undefined,
    description: undefined,
    recipeDescription: [],
    ingredients: [],
    comments: [],
    steps: undefined,
    time: undefined,
    img:undefined
  });

  useEffect(() => {
    console.log("id is: ", id);
    const auth = localStorage.getItem("authenticated")
    setAuthenticated(auth)
    getMealDetails(`${id}`);
    // setShoppingList(checkedValues);
    // checkChangeController();
  }, [0]);

  const [likes, setLikes] = useState(0);
  const [active, setActive] = useState(false);
  const [liked, setLiked] = useState(false);
  const item = [];
  const handleLike = async() => {
    if (active) {
      setLikes(likes - 1);
      setActive(false);
      try {
        const cmt = {
          id: id,
        };
        let url = "https://platemate.herokuapp.com/playmate/recipe/removeLike";
        const response = await axios.post(url, cmt);
        //const { meals } = await response.json();
        console.log("response is: ", response);
      } catch (err) {
        console.error(err);
      }
    } else {
      setLikes(likes + 1);
      setActive(true);
      try {
        const cmt = {
          id: id,
        };
        let url = "https://platemate.herokuapp.com/playmate/recipe/addLike";
        const response = await axios.post(url, cmt);
        //const { meals } = await response.json();
        console.log("response is: ", response);
      } catch (err) {
        console.error(err);
      }
    }
    setLiked(!liked);
    setLikes(liked ? likes - 1 : likes + 1);
  };

  // useEffect(() => {
  //   console.log("Inside useEffect");
  //   if (products.length) {
  //     setCurrentProduct(products.find((product) => product.idMeal === idMeal));
  //   }
  // }, []);

  const getMealDetails = async (query) => {
    console.log("query is: ", query);
    try {
      let url = "https://platemate.herokuapp.com/playmate/recipe/dish?id=" + query;
      const response = await axios.get(url);
      //const { meals } = await response.json();
      console.log("response is: ", response);

      for (let val in response.data) {
        meals.push(response.data[val]);
      }

      console.log("meals is: ", meals);

      const mealData = {
        id: response.data.id,
        name: response.data.name.toUpperCase(),
        description: response.data.description,
        recipeDescription: response.data.procedure,
        ingredients: response.data.ingredients,
        comments: response.data.commentList,
        steps: response.data.noOfSteps,
        time: response.data.time,
        img: response.data.recipeImage
      };

      console.log("meal data is: ", mealData);

      setSavedMeals(mealData);
      setMealDetails(mealData);
      setLikes(response.data.noOfLikes);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      <div className="single-recipe-container">
        {location.pathname !== "/" && (
          <button className="btn back-btn" onClick={() => navigate(-1)}>
            &larr; Go Back
          </button>
        )}
        {
          <div key={mealDetails.id} className="recipe-details">
            <h2>{mealDetails.name}</h2>
            <h6>{mealDetails.description}</h6>
            <div className="recipe-screens">
              <div className="recipe-img-vid">
                <img
                  src={
                    //"https://img.sndimg.com/food/image/upload/q_92,fl_progressive,w_1200,c_scale/v1/img/recipes/33/04/56/picy4DOVx.jpg"
                    mealDetails.img ? mealDetails.img : staticPng
                  }
                  height="275"
                  width="336"
                  alt={mealDetails.name}
                />
              </div>
              <div
                style={{
                  display: "flex",
                  flexDirection: "column",
                  margin: "1% 2%",
                }}
              >
                <h3 style={{ textAlign: "center" }}>Instructions</h3>
                <ul>
                  {mealDetails.recipeDescription.map((point, index) => (
                    <li key={index}>{point}</li>
                  ))}
                </ul>
                {/* <h3>Tags: {meal.strTags}</h3> */}
                <div>
                  <h3>Ingredients: </h3>
                  <ul>
                    {mealDetails.ingredients.map((point, index) => (
                      <li key={index}>{point}</li>
                    ))}
                  </ul>
                </div>
                <div>
                  <h3>Steps</h3>{mealDetails.steps}
                </div>
                <div>
                  <h3>Time</h3>{mealDetails.time} Min
                </div>
              </div>
            </div>
            <div style={{ display: "flex", flexDirection: "column" }}>
              <div
                style={{
                  display: "flex",
                  justifyContent: "center",
                  alignContent: "center",
                }}
              ></div>
            </div>
          </div>
        }

        <div
          className="like-container"
          style={{ display: "flex", flexDirection: "row" }}
        >
          <div style={{display:"flex" , flexDirection : "column"}} >
          <FaThumbsUp
            className={`like-icon ${liked ? "liked" : ""}`}
            onClick={handleLike}
         
         />
          
        <span className="like-count"> count 
        {mealDetails.noOfLikes}</span>
        </div>
         
          <Button
            onClick={handleFavourite}
            // color={favourite ? "primary" : "secondary"}
          >
            {authenticated ? (
                  favourite ? (
                      <StarIcon fontSize="large" />
                  ) : (
                      <StarBorderOutlinedIcon fontSize="large" />
                      )
                ) : (
                     <></>
            )}
          </Button>
          
        </div >
        {/* <CommentCard data={id} key={commentId} /> */}
        
        <CommentCard data={mealDetails} key={`meal-${savedMeals.id}-${commentId}`} />
        
      </div>
    </div>
  );
};

export default RecipeData;
