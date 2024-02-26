import React from "react";
import "./itinerary.css";
import Header from "../../components/header/Header";
import Navbar from "../../components/navbar/Navbar";
import ItinItem from "../../components/itinItem/ItinItem";
import MapImg from "./yellowstone_map.png";

const Itinerary = () => {

    return (
        <div>
            <Navbar />
            <Header type="list" />
            <div className="itineraryContainer">
                <div className="itineraryWrapper">
                <div className="locations">
                    <label>Planned Locations</label>
                    <div className="locationItems">
                    <ItinItem />
                    <ItinItem />
                    <ItinItem />
                    </div>
                </div>
                <div className="mapImage">
                    <img src={MapImg}></img>
                </div>
                </div>
            </div>
        </div>
    )

}

export default Itinerary;