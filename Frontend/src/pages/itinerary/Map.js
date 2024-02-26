import React, { useState, useEffect } from "react";
import { GoogleMap, InfoWindow, Marker } from "@react-google-maps/api";
import axios from "axios";

const markers = [];

function Map() {
  const [activeMarker, setActiveMarker] = useState(null);

  const count = 0;

  const parkModel = {
    userName: "redda279",
  };

  const [loaded, setLoad] = useState();
  const base_url = process.env.REACT_APP_API_URI;

  useEffect(() => {
    console.log(
      "entered the useEffect present in google map marker js file i.e MapMarker.js"
    );
    handleSubmit();
  }, [count]);

  const handleSubmit = async (e) => {
    try {
      const res = await axios.post(
        base_url + "/location/reverse-geocoder",
        parkModel
      );
      console.log(res);
      for (let val in res.data) {
        markers.push(res.data[val]);
      }
    } catch (err) {
      // console.log("login failed!");
      // console.log(err.response.data);
      console.log(err.response.data.message);
    }

    console.log("load value is: ", loaded);
    setLoad(true);
    console.log("load value is: ", loaded);
  };

  const handleActiveMarker = (marker) => {
    if (marker === activeMarker) {
      return;
    }
    setActiveMarker(marker);
  };

  const google = window.google;
  const handleOnLoad = (map) => {
    console.log("handle markers are: ", markers);
    const bounds = new google.maps.LatLngBounds();
    markers.forEach(({ position }) => bounds.extend(position));
    map.fitBounds(bounds);
  };

  if (loaded) {
    console.log("return log loas value is: ", loaded);
    return (
      <GoogleMap
        onLoad={handleOnLoad}
        onClick={() => setActiveMarker(null)}
        mapContainerStyle={{ width: "100vw", height: "100vh" }}
      >
        {markers.map(({ id, name, position }) => (
          <Marker
            key={id}
            position={position}
            onClick={() => handleActiveMarker(id)}
          >
            {activeMarker === id ? (
              <InfoWindow onCloseClick={() => setActiveMarker(null)}>
                <div>{name}</div>
              </InfoWindow>
            ) : null}
          </Marker>
        ))}
      </GoogleMap>
    );
  }
}

export default Map;
