import React, { useEffect, useState } from "react";
import ReactDOM from "react-dom";

import { Divider, Avatar, Grid, Paper } from "@material-ui/core";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import axios from "axios";

import "./styles.css";

const imgLink =
  "https://images.pexels.com/photos/1681010/pexels-photo-1681010.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";

const CommentCard = (props) => {
  const data = [
    {
      imgUrl: imgLink,
      name: "Michel Michel",
      comment:
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean luctus ut est sed faucibus. Duis bibendum ac ex vehicula laoreet. Suspendisse congue vulputate lobortis. Pellentesque at interdum tortor. Quisque arcu quam, malesuada vel mauris et, posuere sagittis ipsum. Aliquam ultricies a ligula nec faucibus. In elit metus, efficitur lobortis nisi quis, molestie porttitor metus. Pellentesque et neque risus. Aliquam vulputate, mauris vitae tincidunt interdum, mauris mi vehicula urna, nec feugiat quam lectus vitae ex.",
    },
  ];

  // const [commentData, setCommentData] = useState({
  //   imgUrl: undefined,
  //   userName: undefined,
  //   comment: undefined
  // });

  const commentData = []
  for (let val in props.data.comments) {
    const cmt = {
      imgUrl: imgLink,
      userName: props.data.comments[val].userName,
      comment: props.data.comments[val].comment
    }
    commentData.push(cmt);
    //commentData.push(props.comments[val]);
  }
  console.log("comments are outside: ", commentData)
  

  const [comments, setComments] = useState(commentData);
  const [currentComment, setCurrentComment] = useState({})
  const [authenticated, setAuthenticated] = useState(false)
  const [loggedUser, setLoggedUser] = useState({})

  useEffect(() => {
    console.log("props value is: ", props)
    // const User = localStorage.getItem("user")
    // setLoggedUser(User)
    const auth = localStorage.getItem("authenticated")
    const User = localStorage.getItem("user")
    if (User === null) {
      const s = "anonymous"
      setLoggedUser(s)
    } else {
      setLoggedUser(User)
    }
    setAuthenticated(auth)
    // if(authenticated === false) {
    //   console.log("entered auth")
    //   const s = "anonymous"
    //   setLoggedUser(s)
    // } else {
    //   const User = localStorage.getItem("user")
    //   setLoggedUser(User)
    // }
  }, [0])

  const addComment =  (newMessage) => {
    console.log("new message is: ", newMessage)
    setCurrentComment(null);
    console.log("logged user after submit is: ", loggedUser)
    if(authenticated === false) {
      console.log("entered auth submit")
      const s = "anonymous"
      setLoggedUser(s)
    } else {
      const User = localStorage.getItem("user")
      setLoggedUser(User)
    }
    const cmt = {
      imgUrl: imgLink,
      userName: loggedUser,
      comment: newMessage,
    };
    console.log("cmt is", cmt)
    
    try {
      let url = "https://platemate.herokuapp.com/playmate/recipe/addComments";
      const data = {
        userName: loggedUser,
        comment: newMessage,
        recipeId: props.data.id
      }
      console.log("logged user is: ", loggedUser, newMessage)
      
      console.log("data being sent to backend is: ", data)
      const response =  axios.post(url, data);
    } catch (err) {
      console.error(err);
    }
    setComments([...comments, cmt]);
  };

  return (
    <div style={{ padding: 14 }} className="App">
      <h3>Comments</h3>
      {comments.map((item, index) => {
        return (
          <Paper style={{ padding: "40px 20px", marginTop: "10px" }}>
            <Grid container wrap="nowrap" spacing={2}>
              <Grid item>
                <Avatar alt="Remy Sharp" src={item.imgUrl} />
              </Grid>
              <Grid  item xs zeroMinWidth>
                <h4 style={{ margin: 0, textAlign: "left" }}>{item.userName}</h4>
                <p style={{ textAlign: "left" }}>{item.comment}</p>
                {/* <p style={{ textAlign: "left", color: "gray" }}>
              posted 1 minute ago
            </p> */}
              </Grid>
            </Grid>
          </Paper>
        );
      })}

      <div style={{ paddingTop: "20px" }} />
      <TextField
        style={{ backgroundColor: "white" }}
        id="outlined-multiline-flexible"
        label="Comment"
        multiline
        fullWidth
        onChange={(e) => {
          setCurrentComment(e.target.value);
        }}
        maxRows={4}
      />
      <div style={{ paddingTop: "10px" }}>
        <Button variant="outlined" onClick={() => addComment(currentComment)}>
          Submit
        </Button>
      </div>
    </div>
  );
};

export default CommentCard;
