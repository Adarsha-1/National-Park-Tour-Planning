import React from "react";
import { Link } from "react-router-dom";
import "./accommodationReview.css";

const AccommodationReview = ({ item }) => {
  return (
    <div className="accommodationReview">
        <h1 className="rating">{item.rating}</h1>
        <span className="userName">{item.userName}</span>
        <span className="reviewDetails">{item.review}</span>
    </div>
  );
};

export default AccommodationReview;