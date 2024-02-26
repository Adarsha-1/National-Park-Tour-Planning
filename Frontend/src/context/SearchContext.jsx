import { createContext, useReducer, useEffect } from "react";

const INITIAL_STATE = {
  nationalState: undefined,
  dates: [],
  pOptions: {
    adult: undefined,
    children: undefined,
  },
};

export const SearchContext = createContext(INITIAL_STATE);

const SearchReducer = (state, action) => {
  switch (action.type) {
    case "NEW_SEARCH":
      return action.payload;
    case "RESET_SEARCH":
      return INITIAL_STATE;
    default:
      return state;
  }
};

export const SearchContextProvider = ({ children }) => {
  const [state, dispatch] = useReducer(SearchReducer, INITIAL_STATE);

  return (
    <SearchContext.Provider
      value={{
        nationalState: state.destination,
        dates: state.date,
        pOptions: state.personOptions,
        dispatch,
      }}
    >
      {children}
    </SearchContext.Provider>
  );
};
