import axios from "axios";
import { useEffect, useState } from "react";

const useFetch = (url) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);
  console.log("Showing URL");
  console.log(url);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const res = await axios.get(url);
        console.log("URL in useEffect", url);
        console.log("data in useEffect");
        console.log(data);
        setData(res.data);
      } catch (err) {
        console.log("There is error in useEffect");
        console.log(err);
        setError(err);
      }
      setLoading(false);
    };
    fetchData();
  }, []);

  const reFetch = async () => {
    setLoading(true);
    try {
      const res = await axios.get(url);
      console.log("URL in useEffectrefetch", url);
      console.log("data in useEffectrefetch");
      console.log(data);
      setData(res.data);
    } catch (err) {
      console.log("error in refetch", err);
      setError(err);
    }
    setLoading(false);
  };
  return { data, loading, error, reFetch };
};
export default useFetch;
