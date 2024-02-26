import React from "react";
import { Link } from "react-router-dom";
import "./parkSearchItem.css";

const ParkSearchItem = ({ item }) => {
  return (
    <div className="parkSearchItem">
      <img
        src={`data:image/jpeg;base64,${item.images[0]}`}
        alt=""
        className="siParkImg"
      />
      <div className="siParkDesc">
        <h1 className="siParkname">{item.parkName}</h1>
        <span className="siParkAddress">{item.address}</span>
        <span className="siParkDescription">{item.description}</span>
      </div>
      <div className="siParkDetails">
        <div className="siParkDetailTexts">
          <span className="siParkPrice">${item.price}</span>
          <Link to={`/parks/${item.parkName}`}>
            <button className="siParkRegisterButton">Register</button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default ParkSearchItem;
