import React from "react";
import "./itinItem.css";

const ItinItem = () => {
    return (
        <div className="ItinItem">
            <div className="description">
                <h1 className ="parkName">Yellowstone National Park</h1>
                <h3 className="location">Wyoming</h3>
            </div>
            <div className="accommInfo">
                <span className="accommName">Campground</span><br/>
                <span className="accommDates">11/01/2022 - 11/8/2022</span><br/>
                <span className="accommPrice">$123</span>
            </div>
            <div className="travelPlans">
                <span className="transportation">Flight</span>
                <span className="company">Airline</span>
                <span className="dates">11/01/2022</span>
            </div>
        </div>
    )
}

export default ItinItem;