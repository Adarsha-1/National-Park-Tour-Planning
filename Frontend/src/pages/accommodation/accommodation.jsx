import "./accommodation.css";
import AccommodationReview from "../../components/accommodationReview/AccommodationReview";
import { useLocation, useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import Navbar from "../../components/navbar/Navbar";
import Header from "../../components/header/Header";
import useFetch from "../../hooks/useFetch";
import { SearchContext } from "../../context/SearchContext";
import { AuthContext } from "../../context/AuthContext";
import axios from "axios";
import { useState, useContext, useEffect } from "react";

const Accommodation = () => {
  const location = useLocation();
  const accommodationName = location.pathname.split("/")[2];
  console.log(accommodationName);

  const base_url = process.env.REACT_APP_API_URI;
  const { loginUser, loginEmail, loginOauth2 } = useContext(AuthContext);

  // search context
  const { dates } = useContext(SearchContext);

  const { data, loading, error, reFetch } = useFetch(
    base_url + `/accommodation/accommodationName/${accommodationName}`
  );

  console.log("Data while loading accomm page");
  console.log(data.accommodationLocation);
  //data.accommodationLocation.accommodationId.id

  const [reviewDetails, setReviewDetails] = useState({
    accommodationId: 0,
    userName: "",
    review: "",
    rating: 1,
  });

  const handleSubmit = async (e) => {
    try {
      reviewDetails.accommodationId =
        data.accommodationLocation.accommodationId.id;
      console.log("Review message ", reviewDetails);
      const res = await axios.post(
        base_url + "/accommodation/review",
        reviewDetails
      );
      console.log("Review response ", res);
    } catch (err) {
      console.log(err.response.data);
    }
  };

  const handleOnChange = (e) => {
    setReviewDetails((prev) => ({ ...prev, [e.target.id]: e.target.value }));
    console.log(reviewDetails);
  };

  // to make backend call to add accommodation
  let accommURLBody = {};

  if (loginOauth2) {
    // this is oauth user
    accommURLBody = {
      email: loginEmail,
      oauth2: loginOauth2,
      parkName: decodeURI(data.accommodationLocation?.parkId.parkName),
      accommodation: decodeURI(accommodationName),
      accFromDate: dates[0].startDate,
      accToDate: dates[0].endDate,
    };
    console.log("Accommodation booking details", accommURLBody);
  } else {
    // this is normal login user

    accommURLBody = {
      userName: loginUser,
      parkName: decodeURI(data.accommodationLocation?.parkId.parkName),
      accommodation: decodeURI(accommodationName),
      accFromDate: dates[0].startDate,
      accToDate: dates[0].endDate,
    };
    console.log("Accommodation booking details", accommURLBody);
  }

  const navigate = useNavigate();
  const handleBook = async (e) => {
    try {
      console.log("Sending accommodation details to backend", accommURLBody);
      const res = await axios.post(
        base_url + "/itinerary/add/accommodation",
        accommURLBody
      );
      console.log(res);
      navigate("/itinerary");
    } catch (err) {
      console.log(err.response.data.message);
    }
  };

  return (
    <div>
      <Navbar />
      <Header type="list" />
      <div className="accommodationContainer">
        <div className="accommodationWrapper">
          <span className="titleWrapper">
            <h1 className="accommodationTitle">{data.name}</h1>
          </span>

          <h3 className="accommodationAddress">
            {data.accommodationLocation?.parkId.parkName}
          </h3>
          <div className="accommodationDetails">
            <div className="accommodationDetailsTexts">
              <p className="description">{data.description} Details</p>
            </div>
          </div>
          <span>
            <button onClick={handleBook} className="bookingButton">
              Book Now - ${data.price}/night
            </button>
          </span>
          <div className="reviewsSection">
            {loading ? (
              "Loading ... Please wait"
            ) : (
              <>
                {data.reviewList?.map((item, i) => (
                  <AccommodationReview item={item} key={i} />
                ))}
              </>
            )}
          </div>
          <div className="leaveReview">
            <h3>Did you stay here? Leave a review</h3>
            <form>
              <label for="userName">
                Please enter name you want associated with review
              </label>{" "}
              <br />
              <input type="text" id="userName" onChange={handleOnChange} />{" "}
              <br />
              <label for="rating">Rating:</label> <br />
              <input
                type="number"
                id="rating"
                min="1"
                max="5"
                onChange={handleOnChange}
              />{" "}
              <br />
              <label for="review"> Review:</label> <br />
              <input type="text" id="review" onChange={handleOnChange} /> <br />
            </form>
            <button className="submitReviewbutton" onClick={handleSubmit}>
              Submit Review
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Accommodation;
