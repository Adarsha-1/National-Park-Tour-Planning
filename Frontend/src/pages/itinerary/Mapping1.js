import { useLoadScript } from "@react-google-maps/api";
import Maps from "./Mapping";
import Test from './ItineraryData';
import { useEffect } from "react";
//import "./styles.css";

export default function Mapping1() {
  const { isLoaded } = useLoadScript({
    googleMapsApiKey: "AIzaSyCS2aVvi0tv2YxHQ6bviwU1BNs2htWwDao" // Add your API key
  });

  let item = {}

  return isLoaded ? <Maps key={item}/> : null;
  //return <Maps />
}
