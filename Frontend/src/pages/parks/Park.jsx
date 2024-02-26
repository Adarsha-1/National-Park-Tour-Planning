import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
import Header from "../../components/header/Header";
import Navbar from "../../components/navbar/Navbar";
import "./park.css";
import { useLocation, useNavigate } from "react-router-dom";
import useFetch from "../../hooks/useFetch";
import { useContext } from "react";
import { SearchContext } from "../../context/SearchContext";
import { AuthContext } from "../../context/AuthContext";
import axios from "axios";

const Park = () => {
  const location = useLocation();
  const parkName = location.pathname.split("/")[2];
  console.log("showing park name");
  console.log(parkName);

  const base_url = process.env.REACT_APP_API_URI;
  const { loginUser, loginEmail, loginOauth2 } = useContext(AuthContext);

  const { data, loading, error, reFetch } = useFetch(
    base_url + `/search/park/${parkName}`
  );

  console.log("data from api");
  console.log(data);

  // search context
  const { dates, pOptions } = useContext(SearchContext);

  // Function referred from - https://github.com/safak/youtube2022/blob/mern-booking
  const MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;
  function dayDifference(date1, date2) {
    const timeDiff = Math.abs(date2.getTime() - date1.getTime());
    const diffDays = Math.ceil(timeDiff / MILLISECONDS_PER_DAY);
    return diffDays + 1;
  }

  const days = dayDifference(dates[0].endDate, dates[0].startDate);
  console.log("Number of days", days);

  // console.log("Sending backend: ", {
  //   userName: loginUser,
  //   email: loginEmail,
  //   parkName: decodeURI(parkName),
  //   numOfAdults: pOptions.adult,
  //   numOfChildren: pOptions.children,
  //   numOfDays: days,
  //   parkFromDate: dates[0].startDate,
  //   parkToDate: dates[0].endDate,
  //   oauth2: loginOauth2,
  // });

  const navigate = useNavigate();
  const handleBook = async (e) => {
    try {
      const res = await axios.post(base_url + "/itinerary/add/park", {
        userName: loginUser,
        email: loginEmail,
        parkName: decodeURI(parkName),
        numOfAdults: pOptions.adult,
        numOfChildren: pOptions.children,
        numOfDays: days,
        parkFromDate: dates[0].startDate,
        parkToDate: dates[0].endDate,
        oauth2: loginOauth2,
      });
      console.log(res);
      navigate(`/parks/${parkName}/search`);
    } catch (err) {
      console.log(err.response.data.message);
    }
  };

  return (
    <div>
      <Navbar />
      <Header type="list" />
      {loading ? (
        "Loading... Please wait"
      ) : (
        <div className="parkContainer">
          <div className="parkWrapper">
            <h1 className="parkTitle">{data.parkName}</h1>
            <div className="parkAddress">
              <FontAwesomeIcon icon={faLocationDot} />
              <span>{data.address}</span>
            </div>
            <div className="parkImages">
              {data.images?.map((photo, i) => (
                <div className="parkImgWrapper" key={i}>
                  <img
                    src={`data:image/jpeg;base64,${photo}`}
                    alt=""
                    className="parkImg"
                  />
                </div>
              ))}
            </div>
            <div className="parkDetails">
              <div className="parkDetailsText">
                <h1 className="parkTitle">{data.parkName}</h1>
                <p className="parkDesc">{data.description}</p>
              </div>
              <div className="parkDetailsPrice">
                <h1>Among the animals</h1>
                <h2>
                  <b>
                    $
                    {(
                      days *
                      (data.price * pOptions.adult +
                        (data.price / 2) * pOptions.children)
                    ).toFixed(2)}
                  </b>{" "}
                  {days} day
                </h2>
                <button onClick={handleBook}>Book Now!</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Park;
