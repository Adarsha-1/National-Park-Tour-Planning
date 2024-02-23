import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import AccountMenu from "../accountmenu";
import AdminMenu from "../accountmenu/AdminMenu";

import "bootstrap/dist/css/bootstrap.min.css";
import { Nav, Navbar, Row, Col } from "react-bootstrap";
import platemate from "../../assets/platemate.jpeg"

import "./header.css";

const Header = () => {
  const { loginUser, loginEmail, loginOauth2, dispatch } = useState();
  const [authenticated, setAuthenticated] = useState(false)
  const [loggedUser, setLoggedUser] = useState()
  const [admin, setAdmin] = useState(false)
  //console.log("login details: ");
  //console.log(loginUser, loginEmail, loginOauth2);

  const navigate = useNavigate();

  useEffect(() => { 
    const User = localStorage.getItem("user")
    console.log("local storage value is: ", User)
    setAdmin(false)
    if (User != null && User != "administrator") {
      setAuthenticated(true)
      localStorage.setItem("authenticated", true)
      setLoggedUser(User)
      console.log("logged username is: ", loggedUser)
    } else if(User === "administrator") {
      setAdmin(true)
    }
  })

  const handleLogOut = () => {
    //localStorage.clear()
    //window.location.reload();
    console.log("entered logout")
    navigate("/login")
  } 

  return (
    <div className="container-fluid">
      <div className="navbar-header">
      <a href="/">
        <img src={platemate} height = "70px" width = "150px" alt="Description of the image" />
      </a>
      </div>
      <ul className="nav navbar-nav navbar-right">
        <li>
          <Link to="/search" className="link">
            Search
          </Link>
        </li>
        {loggedUser ? (
  <AccountMenu />
) : admin ? (
  <AdminMenu />
) : (
  <>
    <li>
      <Link to="/login" className="link">
        Login
      </Link>
    </li>
    <li>
      <Link to="/register" className="link">
        Register
      </Link>
    </li>
  </>
)}
      </ul>
    </div>
  );
};

export default Header;
