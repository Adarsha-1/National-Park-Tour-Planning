import React from "react";
import "./list.css";
import Header from "../../components/header/Header";
import Navbar from "../../components/navbar/Navbar";
import format from "date-fns/esm/fp/format/index.js";
import { DateRange } from "react-date-range";
import SearchItem from "../../components/accomSearchItem/SearchItem";
import { useLocation } from "react-router-dom";
import { useState } from "react";
import useFetch from "../../hooks/useFetch.jsx";

const List = () => {
  const location = useLocation();
  const [destination, setDestination] = useState("Arizona");
  //const [date, setDate] = useState(location.state.date);
  const [openDate, setOpenDate] = useState(false);
    const parkName = location.pathname.split("/")[2];

  const base_url = process.env.REACT_APP_API_URI;

  const { data, loading, error, reFetch } = useFetch(
    base_url + `/accommodation/park/${parkName}`
  );
  return (
    <div>
      <Navbar />
      <Header type="list" />
      <div className="listContainer">
        <div className="listWrapper">
          <div className="listSearch">
            <h1 className="lsTitle">List Search</h1>
            <div className="lsItem">
              <label>Accommodation Name</label>
              <input type="text" placeholder="Yellowstone" />
            </div>

            <div className="lsItem">
              <label>Options</label>
              <div className="lsOptions">
                <div className="lsOptionItem">
                  <span className="lsOptionText">
                    Min Price<small>per night</small>
                  </span>
                  <input type="number" className="lsOptionInput" />
                </div>
                <div className="lsOptionItem">
                  <span className="lsOptionText">
                    Max Price<small>per night</small>
                  </span>
                  <input type="number" className="lsOptionInput" />
                </div>
                <div className="lsOptionItem">
                  <span className="lsOptionText">Adults</span>
                  <input
                    type="number"
                    className="lsOptionInput"
                    placeholder={1}
                    min={1}
                  />
                </div>
                <div className="lsOptionItem">
                  <span className="lsOptionText">Children</span>
                  <input
                    type="number"
                    className="lsOptionInput"
                    placeholder={1}
                    min={0}
                  />
                </div>
              </div>
            </div>
            <button>Search</button>
          </div>
          <div className="listResult">
            {loading ? (
              "Loading... Please wait"
            ) : (
              <>
                {data.map((item, i) => (
                  <SearchItem item={item} key={i} />
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