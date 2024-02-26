import React, { useEffect, useState } from "react";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import "./payment.css";
import Header from "../../components/header/Header";
import Navbar from "../../components/navbar/Navbar";
import validator from "validator";
import axios from "axios";
import { AuthContext } from "../../context/AuthContext";
import { useCreditCardValidator, images } from "react-creditcard-validator";

const Payment = () => {
  const [errorMessage, setErrorMessage] = useState("");
  const [secondError, setSecondError] = useState("");
  const [price, setPrice] = useState(123);
  const navigate = useNavigate();
  const [buttonDisabled, setButtonDisabled] = useState(true);

  const base_url = process.env.REACT_APP_API_URI;

  function expDateValidate(month: string, year: string) {
    if (Number(year) > 2035) {
      return "Expiry Date Year cannot be greater than 2035";
    }
    return;
  }

  const {
    getCardNumberProps,
    getCardImageProps,
    getCVCProps,
    getExpiryDateProps,
    meta: { erroredInputs },
  } = useCreditCardValidator({ expiryDateValidator: expDateValidate });

  const [creditCardDetails, setCreditCardDetails] = useState({
    cardNumber: "",
    expiryDate: "",
    cvc: "",
  });

  const handleCreditChange = (e) => {
    setCreditCardDetails((prev) => ({
      ...prev,
      [e.target.id]: e.target.value.trim(),
    }));
    console.log("Credit card Details:", creditCardDetails);
    if (
      creditCardDetails.cardNumber.length != 19 ||
      creditCardDetails.expiryDate.length != 7 ||
      creditCardDetails.cvc.length != 2
    ) {
      console.log("Incomplete card details");
      setButtonDisabled(true);
      console.log(
        creditCardDetails.cardNumber.length,
        creditCardDetails.expiryDate.length,
        creditCardDetails.cvc.length
      );
    } else {
      console.log("complete card details");
      setButtonDisabled(false);
      console.log(
        creditCardDetails.cardNumber.length,
        creditCardDetails.expiryDate.length,
        creditCardDetails.cvc.length
      );
    }
  };

  // const handleButtonDisable = (e) => {
  //   if ()
  //   return false;
  // };

  const { loginUser, loginEmail, loginOauth2, dispatch } =
    useContext(AuthContext);

  console.log(loginUser);
  console.log(loginEmail);
  console.log(loginOauth2);

  let payURL = "";
  let payURLBody = {};

  if (loginOauth2) {
    // this is oauth user
    payURL = base_url + "/pay/test2";
    payURLBody = { email: loginEmail, oath2: loginOauth2 };
  } else {
    // this is normal login user
    payURL = base_url + "/pay/test";
    payURLBody = { userName: loginUser };
  }

  useEffect(() => {
    async function getPrice() {
      try {
        const userPrice2 = await axios.post(payURL, payURLBody);
        console.log(userPrice2);
        console.log(userPrice2.data);
        setPrice(userPrice2.data);
      } catch (err) {
        console.log(err.response.data);
      }
    }

    getPrice();
  });

  const onSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post(base_url + "/pay", payURLBody);
      console.log(res);

      navigate("/");
    } catch (err) {
      setSecondError("You have already paid");
      console.log(err.response.data);
    }
  };

  return (
    <div>
      <Navbar />
      <Header type="list" />
      <div className="paymentContainer">
        <div className="paymentWrapper">
          <div className="cost">
            <h1 className="totalCostHeader">Your total is:</h1>
            <span>$ {price.toFixed(2)}</span>
            <br />
          </div>

          <div className="input-group">
            <svg {...getCardImageProps({ images })} />
            <label>Card Number</label>
            <input
              className="inputpayment"
              {...getCardNumberProps({ onChange: handleCreditChange })}
            />

            <small>
              {erroredInputs.cardNumber && erroredInputs.cardNumber}
            </small>
          </div>
          <div className="multi-input">
            <div className="input-group">
              <label>Valid Till</label>
              <input
                className="inputpayment"
                {...getExpiryDateProps({ onChange: handleCreditChange })}
              />
              <small>
                {erroredInputs.expiryDate && erroredInputs.expiryDate}
              </small>
            </div>

            <div className="input-group">
              <label>CVC</label>
              <input
                className="inputpayment"
                {...getCVCProps({ onChange: handleCreditChange })}
              />
              <small>{erroredInputs.cvc && erroredInputs.cvc}</small>
            </div>
          </div>
          <br />
          <div className="input-group">
            <form onSubmit={onSubmit}>
              <button
                disabled={buttonDisabled}
                className="paymentSubmitBtn"
                type="submit"
              >
                Pay now!
              </button>
              <span
                style={{
                  fontWeight: "bold",
                  color: "red",
                }}
              >
                {secondError}
              </span>{" "}
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Payment;
