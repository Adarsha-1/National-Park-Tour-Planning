import React from 'react'
import { Link } from 'react-router-dom'
import "./searchItem.css"

const SearchItem = ({ item }) => {
    return (
        <div className="searchItem">
            <img src="https://www.nps.gov/yell/planyourvisit/images/bb.jpg?maxwidth=1200&maxheight=1200&autorotate=false"
             alt="" className="siImg" />
             <div className="siDesc">
                <h1 className="siTitle">{item.name}</h1>
                <span className="siLocation">{item.location}</span>
                <span className="siSubtitle">{item.type}</span>
             </div>
             <div className="siDetails">
                <div className="siDetailTexts">
                    <span className="siPrice">${item.price}</span>
                    <Link to={`/accommodation/${item.name}`} >
                        <button className="siCheckButton">See availability</button>
                    </Link>
                </div>
             </div>
        </div>
    )
}

export default SearchItem