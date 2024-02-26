import { createContext, useReducer, useEffect } from "react";

const INITIAL_STATE = {
  loginUser: JSON.parse(localStorage.getItem("loginUser")) || null,
  loginEmail: null,
  loginOauth2: false,
  loginLoad: null,
  loginError: null,
};

export const AuthContext = createContext(INITIAL_STATE);

const AuthReducer = (state, action) => {
  switch (action.type) {
    case "LOGIN_START":
      return {
        loginUser: null,
        loginEmail: null,
        loginOauth2: false,
        loginLoad: true,
        loginError: null,
      };
    case "LOGIN_SUCCESS":
      return {
        loginUser: action.payload.userName,
        loginEmail: action.payload.email,
        loginOauth2: action.payload.oauth2,
        loginLoad: false,
        loginError: null,
      };
    case "LOGIN_FAIL":
      return {
        loginUser: null,
        loginEmail: null,
        loginOauth2: false,
        loginLoad: false,
        loginError: action.payload,
      };
    case "LOGOUT":
      return {
        loginUser: null,
        loginEmail: null,
        loginOauth2: false,
        loginLoad: false,
        loginError: null,
      };
    default:
      return state;
  }
};

export const AuthContextProvider = ({ children }) => {
  const [state, dispatch] = useReducer(AuthReducer, INITIAL_STATE);

  useEffect(() => {
    localStorage.setItem("loginUser", JSON.stringify(state.loginUser));
  }, [state.loginUser]);

  return (
    <AuthContext.Provider
      value={{
        loginUser: state.loginUser,
        loginEmail: state.loginEmail,
        loginOauth2: state.loginOauth2,
        loginLoad: state.loginLoad,
        loginError: state.loginError,
        dispatch,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
