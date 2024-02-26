import { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Login.css";
import Navbar from "../../components/navbar/Navbar";
import { AuthContext } from "../../context/AuthContext";
import GoogleIcon from "../../assets/google1.png";

// google login
import { GoogleLogin } from "react-google-login";
import { gapi } from "gapi-script";

const Login = () => {
  // for password credentials
  const [credentials, setCredentials] = useState({
    userName: undefined,
    password: undefined,
  });

  // Login - Authentication context
  const { loginLoad, loginError, dispatch } = useContext(AuthContext);

  // navigation on authentication success
  const navigate = useNavigate();

  useEffect(() => {
    const initClient = () => {
      gapi.auth2.init({
        clientId: clientId,
        scope: "",
      });
    };
    gapi.load("client:auth2", initClient);
  });

  const handleChange = (e) => {
    setCredentials((prev) => ({ ...prev, [e.target.id]: e.target.value }));
  };

  const base_url = process.env.REACT_APP_API_URI;
  const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;

  const handleSubmit = async (e) => {
    console.log("Called submit");
    console.log(credentials);
    e.preventDefault();
    dispatch({ type: "LOGIN_START" });
    try {
      const res = await axios.post(base_url + "/user/login", credentials);
      console.log("Normal login", res);
      dispatch({ type: "LOGIN_SUCCESS", payload: res.data });
      navigate("/");
    } catch (err) {
      console.log(err.response.data.message);
      dispatch({ type: "LOGIN_FAIL", payload: err.response.data });
    }
  };

  const onGoogleLoginSuccess = async (gres) => {
    console.log("Successful login", gres);
    console.log("Email", gres.profileObj.email);
    console.log("Name", gres.profileObj.givenName);
    dispatch({ type: "LOGIN_START" });
    try {
      const res = await axios.post(base_url + "/user/create/oauth2/sign", {
        email: gres.profileObj.email,
        name: gres.profileObj.givenName,
      });
      console.log("Google Auth login", res);
      dispatch({ type: "LOGIN_SUCCESS", payload: res.data });
      navigate("/");
    } catch (err) {
      console.log(err);
      console.log(err.response.data.message);
      dispatch({ type: "LOGIN_FAIL", payload: err.response.data });
    }
  };

  const onFailure = (err) => {
    console.log("Login failed", err);
  };

  return (
    <div>
      <Navbar />
      <div className="loginContainer">
        <h1 className="loginMethodTitle">Choose Login Method</h1>
        <div className="wrapper">
          <div className="leftPanel">
            {/* start of google login */}
            {/* <div className="loginButton">
              <img src={GoogleIcon} alt="" className="icon" />
              Google
            </div> */}
            <GoogleLogin
              clientId={clientId}
              buttonText="Sign in with Google"
              onSuccess={onGoogleLoginSuccess}
              onFailure={onFailure}
              cookiePolicy={"single_host_origin"}
              isSignedIn={true}
            />
            {/* end of google login */}
          </div>
          <div className="centerPanel">
            <div className="line"></div>
          </div>
          <div className="rightPanel">
            <input
              className="loginInput"
              type="text"
              id="userName"
              onChange={handleChange}
              placeholder="User Name"
            />
            <input
              className="loginInput"
              type="password"
              id="password"
              onChange={handleChange}
              placeholder="Password"
            />
            <button
              onClick={handleSubmit}
              disabled={loginLoad}
              className="submit"
            >
              Login
            </button>
            {loginError && (
              <span className="LerrorMessageSpan">{loginError.message}</span>
            )}
            <span className="psw">
              <a href="/forgot">Forgot password/user name</a>
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
