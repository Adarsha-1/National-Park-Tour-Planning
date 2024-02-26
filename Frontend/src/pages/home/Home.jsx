import Featured from "../../components/featured/Featured";
import FeatureProperties from "../../components/featureProperties/FeatureProperties";
import Header from "../../components/header/Header";
import Navbar from "../../components/navbar/Navbar";
import RideList from "../../components/rideList/RideList";
import "./home.css";

const Home = () => {
  return (
    <div>
      <Navbar />
      <Header />
      <div className="homeContainer">
        <Featured />
        <h1 className="homeTitle">Browse by ride type</h1>
        <RideList />
        {/* <h1 className="homeTitle">Homes you'll like</h1> */}
        <FeatureProperties />
      </div>
    </div>
  );
};

export default Home;
