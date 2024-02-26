import React from "react";
import { useEffect } from "react";
import axios from "axios";

import "./itinItem.css";
import Header from "../../components/header/Header";
import Navbar from "../../components/navbar/Navbar";
import ItinItem from "../../components/itinItem/ItinItem";
import { useState, useContext } from "react";
import { AuthContext } from "../../context/AuthContext";
import MapImg from "./Fall-in-Yellowstone-River-Meadows.jpeg";

function ItineraryData() {
  const { loginUser } = useContext(AuthContext);

  const parkModel = {
    userName: loginUser,
  };

  let itineraryData = {};

  let count = 0;

  const [details, setDetails] = useState([]);

  let itineraryLength = 0;

  const base_url = process.env.REACT_APP_API_URI;

  let keys = {};
  useEffect(() => {
    console.log("itinerary planned items load");
    //if(!!parkModel) {
    //handleSubmit()
    //}
    //handleSubmit()
    //ItineraryRow(itineraryData)

    axios
      .post(base_url + "/itinerary/full", parkModel)
      .then((res) => {
        console.log("result is: ", res.data);
        setDetails(res.data);
        console.log("details are: ", details);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [count]);

  const handleSubmit = async (e) => {
    //console.log("called number of times: ");
    //e.preventDefault();
    //console.log("parkModel", parkModel)

    if (!!parkModel) {
      //setParkModel("redda279", undefined, undefined)
      console.log("inside loop", parkModel);
      parkModel = {
        userName: "redda279",
      };
      console.log("entered again:", parkModel);
    }
    try {
      console.log("trying to connet to backend");
      const res = await axios.post(base_url + "/itinerary/full", parkModel);
      console.log("result is", res.data[0].parkName);
      itineraryData = res.data;
      console.log("itinerary data is: ", itineraryData.length);
      itineraryLength = itineraryData.length;
      //setDetails(res.data)
      console.log("keys are", Object.keys(itineraryData));
      console.log("details are: ", details);
      keys = Object.keys(itineraryData);
    } catch (err) {
      console.log(err.response.data);
    }
  };

  function handleDelete(parkName) {
    console.log("entered with parkName delete:  ", parkName);
  }

  //if(itineraryLength == 0) {
  //console.log("entered if condition")
  //return null
  //}

  //const userTable = itineraryData.map((itinerary) => ItineraryRow(itinerary))

  //if (todos.length == 4){
  return details.map((todo, index) => (
    <div key={index} className="column">
      <div className="card">
        <img src={MapImg} alt="John" width="100%"></img>
        <h1>{todo.parkName}</h1>
        <p className="title">{todo.accommodationName}</p>
        <p>
          {todo.accFromDate} - {todo.accToDate}
        </p>
        <p className="title">{todo.transportation}</p>
        <p>{todo.transStartDate}</p>
        <div margin="24px 0;"></div>
        <p>
          <button className="b" onClick={handleDelete(todo.parkName)}>
            Delete
          </button>
        </p>
      </div>
    </div>
  ));
  //}
}

export default ItineraryData;
