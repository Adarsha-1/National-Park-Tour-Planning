import React, { useState, useEffect, useContext } from "react";
import {
  GoogleMap,
  InfoWindow,
  Marker,
  MarkerF,
  useLoadScript,
} from "@react-google-maps/api";

import "./itinerary.css";
import Header from "../../components/header/Header";
import Navbar from "../../components/navbar/Navbar";
import ItinItem from "../../components/itinItem/ItinItem";
import MapMarker from "./MapMarker";
import axios from "axios";
import ItineraryData from "./ItineraryData";
import { AuthContext } from "../../context/AuthContext";
import MapImg from "./Fall-in-Yellowstone-River-Meadows.jpeg";
import { useNavigate } from "react-router-dom";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';


const markers = [

];

function Mapping() {
  const navigate = useNavigate();
  const [activeMarker, setActiveMarker] = useState(null);

  const { loginUser, loginEmail, loginOauth2, dispatch } =
    useContext(AuthContext);


  const parkModel = {
      userName: loginUser,
      email: loginEmail,
      oauth2: loginOauth2
  };

  const { isLoaded } = useLoadScript({
    googleMapsApiKey: "AIzaSyCS2aVvi0tv2YxHQ6bviwU1BNs2htWwDao", // Add your API key
  });

  const base_url = process.env.REACT_APP_API_URI;

  let item = {};

  let item1 = {};


  const handleActiveMarker = (marker) => {
    if (marker === activeMarker) {
      return;
    }
    setActiveMarker(marker);
  };

  const google = window.google;
  const handleOnLoad = (map) => {
    const bounds = new google.maps.LatLngBounds();
    markers.forEach(({ position }) => bounds.extend(position));
    map.fitBounds(bounds);
  };

  

  const arr = [];

  //const { loginUser } = useContext(AuthContext);


  let itineraryData = {};

  let count = 0;

  const [details, setDetails] = useState([]);

  let itineraryLength = 0;

  let keys = {};
  useEffect(() => {
    console.log("itinerary planned items load");
    //if(!!parkModel) {
    //handleSubmit()
    //}
    //handleSubmit()
    //ItineraryRow(itineraryData)

    console.log("park model ia: ", parkModel)
    const parkModel2 = {
      userName: loginUser,
      email: loginEmail,
      oauth2: loginOauth2,
    };
    console.log("logged in user is: ", loginUser)
    console.log("Park model2 created is: ", parkModel2)

    axios
      .post(base_url + "/itinerary/full", parkModel2)
      .then((res) => {
        console.log("result is: ", res.data);
        setDetails(res.data);
        console.log("details are: ", details);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [count]);

  const notify = () => {
      toast.error("Itinerary is already completed. Can't delete it!", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "dark",
        });
  }

  function handleDelete(parkName) {
    console.log("entered with parkName delete:  ", parkName);
    const parkModel1 = {
      userName: loginUser,
      email: loginEmail,
      oauth2: loginOauth2,
      parkName: parkName,
    };
    axios
      .put(base_url + "/itinerary/delete/park", parkModel1)
      .then((res) => {
        console.log("delete data is: ", res.data);
        setDetails(res.data);
      })
      .catch((err) => {
        console.log(err);
        let msg = err.response.data.message
        if (msg === 'The Itinerary is completed') {
          notify()
        }
      });
  }

  function handleContinue() {

    console.log("clicked on continue option")
    navigate("/payment")
  }

  if (isLoaded) {
    return (
      <div>
        <Navbar />
        <Header type="list" />
        <div className="row re">
          {details.map((todo, index) => (
            <div key={index} className="column">
              <div className="card">
                <img src={MapImg} alt="John" width="100%"></img>
                <h1>{todo.parkName}</h1>
                <p>
                  {todo.parkFromDate} - {todo.parkToDate}
                </p>
                <p className="title">{todo.accommodationName}</p>
                <p>
                  {todo.accFromDate} - {todo.accToDate}
                </p>
                <p className="title">{todo.transportation}</p>
                <p>{todo.transStartDate}</p>
                <div margin="24px 0;"></div>
                <p>
                  <button
                    className="b"
                    onClick={() => handleDelete(todo.parkName)}
                  >
                    Delete
                  </button>
                </p>
              </div>
            </div>
          ))}
        </div>
        <div className="parkContainer">
          <MapMarker key={item} />
        </div>
        <div className="submitContainer">
          <button className="b1" onClick={handleContinue}
                    >Continue</button>
        </div>
        <ToastContainer />
      </div>
    );
  }
}

export default Mapping;
