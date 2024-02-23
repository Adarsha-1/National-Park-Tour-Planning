import React, { useContext, useState, useEffect } from "react";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import UploadFileIcon from "@mui/icons-material/UploadFile";
import Button from "@mui/material/Button";
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const NewRecipe = () => {
  const [recipeName, setRecipeName] = useState("");

  const [description, setDescription] = useState("");
  const [steps, setSteps] = useState("");
  const [ingredients, setIngredients] = useState("");
  const [noOfSteps, setNoOfSteps] = useState("");
  const [time, setTime] = useState("");

  const handleRecipeNameChange = (event) => {
    setRecipeName(event.target.value);
  };

  const handleDescription = (event) => {
    setDescription(event.target.value)
  }

  const handleSteps = (event) => {
    setSteps(event.target.value)
  }

  const handleIngredients = (event) => {
    setIngredients(event.target.value)
  }

  const handleNoOfSteps = (event) => {
    setNoOfSteps(event.target.value)
  }

  const handleTime = (event) => {
    setTime(event.target.value)
  }

  const [selectedFile, setSelectedFile] = useState(null);

  const handleFileSelection = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleFileUpload = () => {
    // code to handle file upload
    console.log("Selected file:", selectedFile);
  };

  const notify = () => {
    toast.success('Recipe Added Successfully', {
      position: "top-center",
      autoClose: 4000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
      });
  }
  const resetForm = () => {
    setRecipeName("");
    setDescription("");
    setSteps("");
    setIngredients("");
    setNoOfSteps("");
    setTime("");
  };

  const handleSubmit = async() => {
    console.log("recipe details are: ")
    const recipeDetails = {
      name : recipeName,
      description : description,
      time: time,
      noOfSteps: noOfSteps,
      recipeProcedure: steps,
      ingredients: ingredients, 
    }
    console.log("recipe model is: ", recipeDetails)
    try {
      const userName = localStorage.getItem("user")
      let url = "https://platemate.herokuapp.com/playmate/recipe/addRecipe?userName=" +userName
      const response = await axios.post(url, recipeDetails); 
      notify();
      resetForm();

  } catch (err) {
      console.error(JSON.parse(JSON.stringify(err)));
  }
  }

  return (
    <div style={{ backgroundColor: "#E9FFC8" }}>
      <Paper
        elevation={3}
        sx={{ marginRight: "15%", marginLeft: "15%", marginTop: "10px" }}
      >
        <Box sx={{ padding: 5 }}>
          <Typography variant="h6" gutterBottom sx={{ paddingBottom: 5 }}>
            New Recipe
          </Typography>
          <Grid container spacing={3}>
            <Grid item xs={12} sm={2}>
              <InputLabel
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  fontWeight: 700,
                }}
              >
                Recipe Name
              </InputLabel>
            </Grid>
            <Grid item xs={12} sm={10}>
              <TextField
                required
                id="title"
                name="title"
                label="Name"
                type="text"
                fullWidth
                size="small"
                autoComplete="off"
                variant="outlined"
                value={recipeName}
                onChange={handleRecipeNameChange}
              />
            </Grid>
            <Grid item xs={12} sm={2}>
              <InputLabel
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  fontWeight: 700,
                }}
              >
                Description
              </InputLabel>
            </Grid>
            <Grid item xs={12} sm={10}>
              <TextField
                required
                id="title"
                name="title"
                label="Description"
                type="text"
                fullWidth
                size="small"
                autoComplete="off"
                variant="outlined"
                value={description}
                onChange={handleDescription}
              />
            </Grid>

            <Grid item xs={12} sm={2}>
              <InputLabel
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  fontWeight: 700,
                }}
              >
                Steps
              </InputLabel>
            </Grid>
            <Grid item xs={12} sm={10}>
              <TextField
                required
                id="outlined-multiline-static"
                label="Steps"
                multiline
                fullWidth
                rows={6}
                value={steps}
                onChange={handleSteps}
              />
            </Grid>
            <Grid item xs={12} sm={2}>
              <InputLabel
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  fontWeight: 700,
                }}
              >
                Ingredients
              </InputLabel>
            </Grid>
            <Grid item xs={12} sm={10}>
              <TextField
                required
                id="title"
                name="title"
                label="Ingredients"
                type="text"
                fullWidth
                size="small"
                autoComplete="off"
                variant="outlined"
                value={ingredients}
                onChange={handleIngredients}
              />
            </Grid>
            <Grid item xs={12} sm={2}>
              <InputLabel
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  fontWeight: 700,
                }}
              >
                Number of Steps
              </InputLabel>
            </Grid>
            <Grid item xs={12} sm={10}>
              <TextField
                required
                id="url"
                name="url"
                label="Number of Steps"
                fullWidth
                size="small"
                autoComplete="off"
                variant="outlined"
                value={noOfSteps}
                onChange={handleNoOfSteps}
              />
            </Grid>
            <Grid item xs={12} sm={2}>
              <InputLabel
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  fontWeight: 700,
                }}
              >
                Time Taken
              </InputLabel>
            </Grid>
            <Grid item xs={12} sm={10}>
              <TextField
                required
                id="url"
                name="url"
                label="Time Taken"
                fullWidth
                size="small"
                autoComplete="off"
                variant="outlined"
                value={time}
                onChange={handleTime}
              />
            </Grid>
{/* 
            <Grid item xs={12} sm={2}>
              <InputLabel
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  fontWeight: 700,
                }}
              >
                Img Upload
              </InputLabel>
            </Grid>
            <Grid item xs={12} sm={2}>
            <label htmlFor="upload-button">
          <Button component="span" startIcon={<UploadFileIcon />}>
            Upload
          </Button>
        </label>
        <input
          id="upload-button"
          type="file"
          accept=".jpg,.jpeg,.png"
          style={{ display: "none" }}
          onChange={handleFileSelection}
        />
      </Grid>
      <Grid item xs={12} sm={2}>
        <Button
          variant="outlined"
          onClick={handleFileUpload}
          disabled={!selectedFile}
        >
          Upload
        </Button>
            </Grid> */}
            <Grid item xs={12} sm={6} />
            <Grid item xs={12} sm={5} />
            <Grid item xs={12} sm={2}>
              <Button
                variant="contained"
                sx={{ backgroundColor: "#ff781f" }}
                fullWidth
                onClick={handleSubmit}
              >
                Save
              </Button>
            </Grid>
            <Grid item xs={12} sm={5} />
          </Grid>
        </Box>
      </Paper>
      <ToastContainer />
    </div>
  );
};

export default NewRecipe;
