import "./App.css";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Register from "./pages/register/Register";
import Login from "./pages/login/Login";
import Search from "./pages/search/Search";
import RecipeData from "./pages/recipeData/RecipeData";
import { ApolloProvider, ApolloClient, InMemoryCache } from "@apollo/client";
import NewRecipe from "./pages/newrecipe";
import Admin from "./pages/admin";
import Myfavrecipes from "./pages/myfavrecipes/Myfavrecipes";
import MyRecipes from "./pages/myrecipes/myrecipe";
import Userprofile from "./pages/Userprofile/Userprofile";

function App() {
  const client = new ApolloClient({
    // Set up our client to execute the `authLink` middleware prior to making the request to our GraphQL API
    link: "",
    cache: new InMemoryCache(),
  });
  return (
    <ApolloProvider client={client}>
      <BrowserRouter>
        <div className="flex-column justify-flex-start min-100-vh">
          <Header />
          <div className="container-fluid">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
              <Route path="/search" element={<Search />} />
              <Route path="/recipe/:id" element={<RecipeData />} />
              <Route path="/newrecipe" element={<NewRecipe />} />
              <Route path="/admin" element={<Admin />} />
              <Route path="/myfavrecipes" element={<Myfavrecipes />} />
              <Route path="/myrecipes" element={<MyRecipes />} />
              <Route path="/Userprofile" element={<Userprofile />} />
            </Routes>
          </div>
        </div>
      </BrowserRouter>
    </ApolloProvider>
  );
}

export default App;
