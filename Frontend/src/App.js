import "./App.css";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import List from "./pages/list/List";
import Park from "./pages/parks/Park";
import Accommodation from "./pages/accommodation/accommodation";
import AccomList from "./pages/accomSearch/List";
import Login from "./pages/login/Login";
import Itinerary from "./pages/itinerary/Mapping";
import Register from "./pages/register/Register";
import Payment from "./pages/payment/Payment";
import Forgot from "./pages/forgotPassword/forgotPassword";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Home />} />
        <Route path="/parks" element={<List />} />
        <Route path="/accommodation/:id" element={<Accommodation />} />
        <Route path="/parks/:id/search" element={<AccomList />} />
        <Route path="/accomsearch" element={<AccomList />} />
        <Route path="/itinerary" element={<Itinerary />} />
        <Route path="/parks/:id" element={<Park />} />
        <Route path="/register" element={<Register />} />
        <Route path="/payment" element={<Payment />} />
        <Route path="/forgot" element={<Forgot />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
