import { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import "./navbar.css";

const Navbar = () => {
  // Login - Authentication context
  const { loginUser, loginEmail, loginOauth2, dispatch } =
    useContext(AuthContext);
  console.log("login details: ");
  console.log(loginUser, loginEmail, loginOauth2);

  const navigate = useNavigate();

  const handleLogout = () => {
    dispatch({ type: "LOGOUT" });
    navigate("/");
  };

  return (
    <div className="navbar">
      <div className="navContainer">
        <span className="logo">
          <Link to="/" className="link">
            National Park Tour Planning
          </Link>
        </span>
        {loginUser ? (
          <div className="navItems">
            <button className="navButton">
              <Link to="/itinerary" className="link">
                Itinerary
              </Link>
            </button>
            <button onClick={handleLogout} className="navButton">
              Logout
            </button>
          </div>
        ) : (
          <div className="navItems">
            <button className="navButton">
              <Link to="/register" className="link">
                Register
              </Link>
            </button>
            <button className="navButton">
              <Link to="/login" className="link">
                Login
              </Link>
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Navbar;
