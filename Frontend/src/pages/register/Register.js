import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import axios from "axios";
import { useMutation } from "@apollo/client";
import { ADD_USER } from "../../util/mutations";

import "./register.css";

const Register = () => {
  const [formState, setFormState] = useState({
    userName: "",
    email: "",
    password: "",
  });
  const [addUser, { error, data }] = useMutation(ADD_USER);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormState({
      ...formState,
      [name]: value,
    });
  };

  const navigate = useNavigate()

  const handleFormSubmit = async (event) => {
    event.preventDefault();
    console.log(formState);

    try {
      const res = await axios.post(
        "https://platemate.herokuapp.com/playmate/user/register",
        formState
      );
      console.log(res);
      navigate("/login");
    } catch (err) {
      console.log(err.response.data);
    }
  };

  return (
    <main
      className="signup-container"
      style={{ backgroundColor: "#F5FFF0", height: "80vh" }}
    >
      <div className="signup-form">
        <div className="card" style={{ border: "none" }}>
          <h4 className="card-header">Sign Up</h4>
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
                  className="form-input signup-input"
                  placeholder="Your username"
                  name="userName"
                  type="text"
                  value={formState.name}
                  onChange={handleChange}
                />
                <input
                  className="form-input signup-input"
                  placeholder="Your email"
                  name="email"
                  type="email"
                  value={formState.email}
                  onChange={handleChange}
                />
                <input
                  className="form-input signup-input"
                  placeholder="******"
                  name="password"
                  type="password"
                  value={formState.password}
                  onChange={handleChange}
                />
                <button
                  className="signup-btn"
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
                  type="submit"
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

export default Register;
