import { useState } from "react";
import "./formInput.css";

const FormInput = (props) => {
  const { label, validationErrorMessage, onChange, id, ...userInputProps } =
    props;

  const [isFocussed, setIsFocussed] = useState(false);

  const handleisFocussed = (e) => {
    setIsFocussed(true);
  };

  return (
    <div className="formInput">
      <label className="inputLabel">{label}</label>
      <input
        className="userInputField"
        {...userInputProps}
        onChange={onChange}
        onBlur={handleisFocussed}
        onFocus={() =>
          userInputProps.name === "confirmPassword" && setIsFocussed(true)
        }
        isfocussed={isFocussed.toString()}
      />
      <span className="errorMessageSpan">{validationErrorMessage}</span>
    </div>
  );
};

export default FormInput;
