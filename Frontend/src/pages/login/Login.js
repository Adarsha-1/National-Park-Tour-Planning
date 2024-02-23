import React, { useState } from 'react';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { useMutation } from '@apollo/client';
import axios from "axios";
import { LOGIN_USER } from '../../util/mutations';
//import { saveMealIds } from "../utils/localStorage";

import './login.css';

const Login = () => {
  const [formState, setFormState] = useState({ email: '', password: '' });
  const [login, { error, data }] = useMutation(LOGIN_USER);
  const navigate = useNavigate();

  // update state based on form input changes
  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormState({
      ...formState,
      [name]: value,
    });
  };


  // submit form
  const handleFormSubmit = async (event) => {
    event.preventDefault();
    console.log(formState);
    const admins = [
      { email: 'admin1@example.com', password: 'password1' },
      { email: 'djagabat@iu.edu', password: '1234241' },
      { email: 'admin3@example.com', password: 'password3' },
    ];
    const adminAccount = admins.find(admin => admin.email === formState.email && admin.password === formState.password);
    if (adminAccount) {
      localStorage.setItem("user", "administrator");
      navigate("/admin")
    } else {
    try {
        const res = await axios.post("https://platemate.herokuapp.com/playmate/user/login", formState);
        console.log(res);
        //navigate("/login");
        //setting the user data in local storage
        localStorage.setItem('user', res.data.userName);
        localStorage.setItem('email',formState.email);
        //setResult(true)
        // clear form values
        navigate("/")
      } catch (err) {
        console.log(err.response.data);
      }
    }

    // clear form values
    setFormState({
      email: '',
      password: '',
    });
    

  };

  return (
    <main
      className="login-container"
      style={{ backgroundColor: "#F5FFF0", height: "80vh" }}
    >
      <div className="login-form">
        <div className="card" style={{ border: "none" }}>
          <h4 className="card-header">
            Login
          </h4>
          <div
            className="card-body"
            style={{
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              margin: "0 auto",
              padding: "9px 25px 25px 25px",
              backgroundColor: "#FFD2CC",
            }}
          >
            {data ? (
              <p>
                Success! You may now head{" "}
                <Link to="/">back to the homepage.</Link>
              </p>
            ) : (
              <form
                onSubmit={handleFormSubmit}
                style={{
                  textAlign: "center",
                  backgroundColor: "white",
                  padding: "4%",
                  borderRadius: "4px",
                }}
              >
                <input
                  className="form-input login-input"
                  placeholder="Your email"
                  name="email"
                  type="email"
                  value={formState.email}
                  onChange={handleChange}
                />
                <input
                  className="form-input login-input"
                  placeholder="******"
                  name="password"
                  type="password"
                  value={formState.password}
                  onChange={handleChange}
                />
                <button
                  className="login-btn"
                  style={{
                    cursor: "pointer",
                    backgroundColor: "#ABD69D",
                    alignItems: "center",
                    marginTop: "5%",
                    width: "150px",
                    height: "30px",
                    borderRadius: "3px",
                    border: "none",
                  }}
                >
                  Submit
                </button>
              </form>
            )}

            {error && (
              <div className="my-3 p-3 bg-danger text-white">
                {error.message}
              </div>
            )}
          </div>
        </div>
      </div>
    </main>
  );
};

export default Login;
