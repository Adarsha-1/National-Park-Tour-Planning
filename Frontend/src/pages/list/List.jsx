import React, { useContext } from "react";
import { useState } from "react";
import { useLocation } from "react-router-dom";
import Header from "../../components/header/Header";
import Navbar from "../../components/navbar/Navbar";
import "./list.css";

import { format } from "date-fns";
import { DateRange } from "react-date-range";
import ParkSearchItem from "../../components/parkSearchItem/ParkSearchItem";
import useFetch from "../../hooks/useFetch.jsx";
import { SearchContext } from "../../context/SearchContext";

const List = () => {
  // const location = useLocation();
  const [openDate, setopenDate] = useState(false);
  // const [destination, setDestination] = useState(location.state.destination);
  // const [date, setDate] = useState(location.state.date);
  // const [personOptions, setPersonOptions] = useState(
  // location.state.personOptions
  // );

  // search context
  const { nationalState, dates, pOptions, dispatch } =
    useContext(SearchContext);
  console.log("Inside list", nationalState, dates, pOptions);
  const [destination, setDestination] = useState(nationalState);
  const [date, setDate] = useState(dates);
  const [personOptions, setPersonOptions] = useState(pOptions);

  const base_url = process.env.REACT_APP_API_URI;

  const { data, loading, error, reFetch } = useFetch(
    base_url + `/search/${destination}`
  );

  const handleChange = (e) => {
    setDestination(e.target.value);
  };

  const handleSearch = () => {
    console.log("new destination");
    console.log(`${destination}`);
    dispatch({
      type: "NEW_SEARCH",
      payload: { destination, date, personOptions },
    });
    reFetch();
  };

  return (
    <div>
      <Navbar />
      <Header type="list" />
      <div className="listParkContainer">
        <div className="listParkWrapper">
          <div className="listParkSearch">
            <h1 className="listParkTitle">Search</h1>
            <div className="listParkItem">
              <label>Destination</label>
              <input
                type="text"
                placeholder={destination}
                onChange={handleChange}
              />
            </div>
            <div className="listParkItem">
              <label>Check-In Date</label>
              <span onClick={() => setopenDate(!openDate)}>{`${format(
                date[0].startDate,
                "MM/dd/yyyy"
              )} to ${format(date[0].endDate, "MM/dd/yyyy")}`}</span>
              {openDate && (
                <DateRange
                  onChange={(item) => setDate([item.selection])}
                  minDate={new Date()}
                  ranges={date}
                />
              )}
            </div>
            <div className="listParkItem">
              <div className="listParkOptions">
                <label>Options</label>
                <div className="listParkOptionItem">
                  <span className="listParkOptionText">
                    Min Price <small>per entry</small>
                  </span>
                  <input type="number" className="listParkOptionInput" />
                </div>
                <div className="listParkOptionItem">
                  <span className="listParkOptionText">
                    Max Price <small>per entry</small>
                  </span>
                  <input type="number" className="listParkOptionInput" />
                </div>
                <div className="listParkOptionItem">
                  <span className="listParkOptionText">Adults</span>
                  <input
                    type="number"
                    min={1}
                    className="listParkOptionInput"
                    placeholder={personOptions.adult}
                  />
                </div>
                <div className="listParkOptionItem">
                  <span className="listParkOptionText">Children</span>
                  <input
                    type="number"
                    min={0}
                    className="listParkOptionInput"
                    placeholder={personOptions.children}
                  />
                </div>
              </div>
            </div>
            <button onClick={handleSearch}>Search</button>
          </div>
          <div className="listParkResult">
            {loading ? (
              "Loading... Please wait"
            ) : (
              <>
                {data.map((item, i) => (
                  <ParkSearchItem item={item} key={i} />
                ))}
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default List;
