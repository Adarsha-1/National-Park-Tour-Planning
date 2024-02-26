import "./rideList.css";

const RideList = () => {
  return (
    <div className="rideList">
      <div className="rListItem">
        <img
          src="https://cdn.shopify.com/s/files/1/2236/1557/files/AB_Nat_l_Parks_glacier.jpg?v=1595607634"
          alt=""
          className="rListImg"
        />
        <div className="rListTitles">
          <h1>Canoe</h1>
          <h2>National park-Canoe</h2>
        </div>
      </div>
      <div className="rListItem">
        <img
          src="https://www.frommers.com/system/media_items/attachments/000/865/846/s980/Pa'rus_Trail__Zion.jpg?1597006759"
          alt=""
          className="rListImg"
        />
        <div className="rListTitles">
          <h1>Cycling</h1>
          <h2>National park-Cycle</h2>
        </div>
      </div>
      <div className="rListItem">
        <img
          src="https://www.us-parks.com/images/featured/hiking.jpg"
          alt=""
          className="rListImg"
        />
        <div className="rListTitles">
          <h1>Hiking</h1>
          <h2>National park-Hiking</h2>
        </div>
      </div>
      <div className="rListItem">
        <img
          src="https://media.cntraveler.com/photos/6046421327e2fd8ada48aa28/3:4/w_1248,h_1664,c_limit/GrandCanyonCamping-2021-GettyImages-520754458-2.jpg"
          alt=""
          className="rListImg"
        />
        <div className="rListTitles">
          <h1>Camping</h1>
          <h2>National park-Camping</h2>
        </div>
      </div>
    </div>
  );
};

export default RideList;
