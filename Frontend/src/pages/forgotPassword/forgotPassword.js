import React, {Component, useState} from "react";
import Navbar from "../../components/navbar/Navbar";
import "./forgotPassword.css";
import axios from "axios";
import { tr } from "date-fns/locale";
import { faL } from "@fortawesome/free-solid-svg-icons";
import FormInput from "../../components/formInput/FormInput";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from "react-router-dom";


function ForgotPassword() {

    const base_url = process.env.REACT_APP_API_URI;

    const navigate = useNavigate();

    const [select, setSelect] = useState();

    const [isPhone, setIsPhone] = useState();

    const [isEmail, setIsEmail] = useState(true);

    const [isSecurity, setIsSecurity] = useState();

    const [isOtpNotSend, setIsOtpNotSend] = useState(true);

    const [codeVerify, setCodeVerify] = useState()
    const [passwordError, setPasswordError] = useState()

    const [credentials, setCredentials] = useState({
        contactNo: undefined,
        email: undefined,
        securityQuestion: "In what city were you born?",
        securityAnswer: undefined
      });

    const [email, setEmail] = useState()
    const [phone, setPhone] = useState()

    const [resetPasswordCredentials, setResetPasswordCredentials] = useState({
        email: email,
        contactNo: phone,
        oldPassword: undefined,
        newPassword: undefined
    })



    const handleSubmit = async (e)=> {

        e.preventDefault()
    }; 

    const [user, setUser] = useState()
    const[errorMessage, seterrorMessage] = useState()

    function checkIfYes(){
        console.log("entered")
        var myVar = document.getElementById('defect').value;
        console.log("selected value is: ", myVar)
        if (myVar === 'Phone') {
            setIsPhone(true)
            setIsEmail(false)
            setIsSecurity(false)
        } else if(myVar === 'Email') {
            setIsEmail(true)
            setIsPhone(false)
            setIsSecurity(false)
        } else if(myVar === 'Security') {
            setIsEmail(false)
            setIsPhone(false)
            setIsSecurity(true)
        }
    }

    

    const handleOnChange = (e) => {
        console.log(document.getElementById('securityQuestion').value);
      };

      const handleChange = (e) => {
        seterrorMessage("")
        setCredentials((prev) => ({ ...prev, [e.target.id]: e.target.value }));
        setResetPasswordCredentials((prev) => ({ ...prev, [e.target.id]: e.target.value }));
      };

      const handleChangeForForgot = (e) => {
        seterrorMessage("")
        setResetPasswordCredentials((prev) => ({ ...prev, [e.target.id]: e.target.value }));
      };


    const generateOtp = async(e) => {
        console.log("entered for generate otp funcationality email", credentials)
        setCredentials((prev) => ({ ...prev, [e.target.id]: e.target.value }))
        console.log("credentails are: ", credentials)
        var myVar = document.getElementById('defect').value;
        setSelect(myVar)
        console.log("myvar is: ", myVar)
        try {

            const res = await axios.post(
                base_url + "/user/forgot/password?providerSelected=" + myVar,
              credentials
            );
            console.log("generateotp" ,res);
            if (myVar === 'Security') {
                console.log("security question, directly redirect to password page")
                setCodeVerify(true)
            } else {
                setIsOtpNotSend(false)
            }
          } catch (err) {
            // console.log("login failed!");
            // console.log(err.response.data);
            seterrorMessage(err.response.data.message)
            console.log(err.response.data.message);
          }

    }

    const notify = () => {
        toast.info('🦄 one-time password sent successfully', {
            position: "top-right",
            autoClose: 2000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
          });
      }

      const notifySuccess = () => {
        toast.success('🦄 one-time password verified', {
            position: "top-right",
            autoClose: 2000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
          });
          console.log("enter success toastify")
          navigate("/login");
      }

    const resendOtp = async(e) => {
        console.log("entered for generate otp funcationality email", credentials)
        if (select === 'Email') {
            try {
                const res = await axios.post(base_url + "/user/resend/email", credentials);
                console.log(res)
                notify()
            } catch(err) {
                seterrorMessage(err.response.data.message)
            console.log(err.response.data.message);
            }
        }

    }

    function verify(){
        console.log("entered")
    }

    const verifyCode = async(e) => {
        setIsOtpNotSend(false)
        var code = document.getElementById('otp').value;
        console.log("Entered code is: ", code)
        try {

            const res = await axios.get(
                base_url + "/user/forgot/password/code/verify?providerSelected=" + select + "&code=" + code
            );
            console.log(res);
            //setIsOtpNotSend(false)

            setCodeVerify(true)
          } catch (err) {
            // console.log("login failed!");
            // console.log(err.response.data);
            seterrorMessage(err.response.data.message)
            console.log(err.response.data.message);
          }

    }

    const handleForgotSubmit = async (e) => {
        console.log("entered funciton")
        if (select === 'Email') {
            let email = credentials.email
            console.log("email: ", email)
            setEmail(email)
            setCredentials((prev) => ({ ...prev, "email": email }))
        }

        let contactNo = credentials.contactNo
        console.log("emails, contactn: ", email, contactNo)

        setPhone(contactNo)

        console.log("rest password details are: ", resetPasswordCredentials)
        try {
            const res = await axios.post( base_url + "/user/resetPassword?providerSelected=" + select, resetPasswordCredentials);
            console.log(res);
            notifySuccess()

          } catch (err) {
            console.log(err.response.data.message);
            setPasswordError(err.response.data.message)
          }
      }



    if (codeVerify) {
        console.log("select value is: ", select)
        return (
            <div>
              <Navbar />
              <div className="modal-content animate ">
                <div className="imgcontainer">
                <label><h2><b className="bold">Change Password</b></h2></label>
                
                    
                  <div className="container">
                    <br></br>
                    <label><b>New Password</b></label>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input
                      className="inp"
                      type="text"
                      id="oldPassword"
                      placeholder="New Password"
                      onChange={handleChangeForForgot}
                    />
                    <br></br>
                    <label><b>Confirm Password</b></label>
                    
                    <input
                      className="inp"
                      type="password"
                      id="newPassword"
                      placeholder="Password"
                      onChange={handleChangeForForgot}
                    />
                    {
                    passwordError &&
                    <h5 style={{ color: 'red' }}>{passwordError}</h5>
                }
                    <button
                      className="submit" onClick={handleForgotSubmit}
                    >
                      Login
                    </button>
                  </div>
                  <div>
                  </div>
                </div>
              </div>
              
              <ToastContainer />
            </div>
          )
    }


    

        return (
            <div>
            {
                isOtpNotSend ? (
            
            <div>
            <Navbar />
            <div className="modal-content animate ">
                <div className="imgcontainer">
                    <br></br>
                    <label><h2><b className="bold">Select an option for otp generation</b></h2></label>

                    <div className="form-group">
                        <label className="control-label"></label>
                        <select onChange={checkIfYes} className="select form-control" id="defect" name="defect">
                            <option id="Email" value="Email">Email</option>
                            <option id="Phone" value="Phone">Phone</option>
                            <option id="Security" value="Security">Security Question</option>
                        </select>
                </div>
                </div>
                {
                    isPhone ? (
                        <div  className="container">
                            <label><b>Phone Number</b></label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="text" className="inp" placeholder="Enter your contact number" id="contactNo"
                            required pattern="^[0-9]{10}" onChange={handleChange}></input>
                        </div>
                    ) : (
                        <div className="container">
                        </div>
                    )
                }
                {
                    isEmail ? (
                        <div className="container">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <label><b>Email</b></label>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="text" className="inp" placeholder="Enter your Email" id="email" required onChange={handleChange}></input>
                        </div>
                    ) : (
                        <div className="container">
                        </div>
                    )
                }
                {
                    isSecurity ? (
                        <div>
                            <div className="container">
                                <label><b>Email</b></label>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="text" className="inp" placeholder="Enter your Email" id="email" required onChange={handleChange}></input>
                            </div>
                        <div className="container">
                            <label><b>Security Question</b></label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <select name="securityQuestion" id="securityQuestion" onChange={handleChange}>

                                <option >In what city were you born?</option>
                                <option >
                                        What is the name of your favourite pet?
                                </option>
                                <option>What is your mother's maiden name?</option>
                                <option>What high school did you attend?</option>
                            </select>
                        </div>
                        <div className="container">
                            <label><b>Security Answer</b></label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="text" className="inp" placeholder="Enter your answer" id="securityAnswer" required onChange={handleChange}></input>
                        </div>
                        </div>
                    ) : (
                        <div className="container">
                        </div>
                    )
                }
                <div className="submitContainer">
                {
                    errorMessage &&
                    <h5 style={{ color: 'red' }}>{errorMessage}</h5>
                }
                </div>
                <div className="submitContainer">
                    <button type="submit" className="submi" onClick={generateOtp}>Submit</button>
                </div>



            </div>
            </div>
         ) : (
                <div>
                    <Navbar />
                    <div className="modal-content animate ">
                        <div className="imgcontainer">
                        <label><h2><b className="bold">Please enter the One-Time Password to verify the account</b></h2></label>
                            <div className="form-group">
                                <label className="control-label"></label>
                                <input type="text" className="inp" placeholder="Enter code" id="otp" required></input>
                            </div>
                        </div>
                        <div className="submitContainer">
                            {
                                errorMessage &&
                                <h5 style={{ color: 'red' }}>{errorMessage}</h5>
                            }
                        </div>
                        <div className="submitContainer">
                            <div>
                            <button type="submit" className="submi"  onClick={verifyCode}>Validate</button>
                            </div>
                        </div>
                        <div className="submitContainer">
                            <a onClick={resendOtp}>Resend One-Time Password</a>
                        </div>
                    </div>
                </div>
            ) }
            <ToastContainer />
            </div>

            
        )
    
}

export default ForgotPassword;