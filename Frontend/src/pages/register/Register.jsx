import "./register.css";
import FormInput from "../../components/formInput/FormInput";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Navbar from "../../components/navbar/Navbar";

const Register = () => {
  // navigation on successful user creation
  const navigate = useNavigate();
  const base_url = process.env.REACT_APP_API_URI;

  const [userDetails, setUserDetails] = useState({
    firstName: "",
    lastName: "",
    dateOfBirth: "",
    email: "",
    contactNo: "",
    password: "",
    // confirmPassword: "",
    securityQuestion: "birthCity",
    securityAnswer: "",
  });

  const userInput = [
    {
      id: 1,
      name: "firstName",
      type: "text",
      placeholder: "First Name",
      label: "First Name",
      validationErrorMessage:
        "Number of characters must be between 3 to 10 both inclusive.",
      condition: true,
      pattern: "^[A-Za-z0-9]{3,10}$",
    },
    {
      id: 2,
      name: "lastName",
      type: "text",
      placeholder: "Last Name",
      label: "Last Name",
      validationErrorMessage:
        "Number of characters must be between 3 to 10 both inclusive.",
      condition: true,
      pattern: "^[A-Za-z0-9]{3,10}$",
    },
    {
      id: 3,
      name: "dateOfBirth",
      type: "date",
      placeholder: "Date of Birth",
      label: "Date of Birth",
      condition: true,
    },
    {
      id: 4,
      name: "email",
      type: "email",
      placeholder: "Email ID",
      label: "Email ID",
      validationErrorMessage: "Must be a valid Email ID.",
      condition: true,
    },
    {
      id: 5,
      name: "contactNo",
      type: "text",
      placeholder: "Contact Number",
      label: "Contact Number",
      validationErrorMessage: "Contact number must have 10 digits.",
      condition: true,
      pattern: "^[0-9]{10}",
    },
    {
      id: 6,
      name: "password",
      type: "password",
      placeholder: "Password",
      label: "Password",
      validationErrorMessage:
        "Minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character",
      condition: true,
      pattern: `^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,20}$`,
    },
    // {
    //   id: 7,
    //   name: "confirmPassword",
    //   type: "password",
    //   placeholder: "Confirm Password",
    //   label: "Confirm Password",
    //   validationErrorMessage: "Confirm password must match Password.",
    //   condition: true,
    //   pattern: userDetails.password,
    // },
    {
      id: 9,
      name: "securityAnswer",
      type: "text",
      placeholder: "Security Answer",
      label: "Security Answer",
      // validationErrorMessage: "Confirm password must match Password.",
      condition: true,
    },
  ];

  const handleSubmit = async (e) => {
    // console.log(userDetails);
    e.preventDefault();
    try {
      const res = await axios.post(base_url + "/user/register", userDetails);
      // console.log(res);
      navigate("/login");
    } catch (err) {
      console.log(err.response.data);
    }
  };

  const handleOnChange = (e) => {
    setUserDetails({ ...userDetails, [e.target.name]: e.target.value });
    console.log(userDetails);
  };

  return (
    <div>
    <Navbar />
    <div className="registerContainer">
      <form className="registerForm" onSubmit={handleSubmit}>
        <h1 className="registerTitle">Register</h1>
        {userInput.slice(0, -1).map((input) => (
          <FormInput
            key={input.id}
            {...input}
            value={userDetails[input.name]}
            onChange={handleOnChange}
          />
        ))}
        <select
          id="8"
          name="securityQuestion"
          value={userDetails.securityQuestion}
          onChange={handleOnChange}
        >
          <option value="birthCity">In what city were you born?</option>
          <option value="favouritePet">
            What is the name of your favourite pet?
          </option>
          <option value="motherName">What is your mother's maiden name?</option>
          <option value="highSchool">What high school did you attend?</option>
        </select>
        <FormInput
          key={userInput.at(-1).id}
          {...userInput.at(-1)}
          value={userDetails[userInput.at(-1).name]}
          onChange={handleOnChange}
        />
        <button className="registerSubmitButton">Submit</button>
      </form>
    </div>
    </div>
  );
};

export default Register;
