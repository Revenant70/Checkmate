import React, { useState } from "react";

const ConfirmationCodeField = ({ onSubmit }) => {
  const [confirmationCode, setConfirmationCode] = useState([
    "",
    "",
    "",
    "",
    "",
    "",
  ]);
  const inputRefs = Array(6)
    .fill(0)
    .map((_, i) => React.createRef());

  const handleCodeChange = (index, value) => {
    const newCode = [...confirmationCode];
    newCode[index] = value;

    // Move to the next input if the current input is filled
    if (value && index < 5) {
      inputRefs[index + 1].current.focus();
    }

    setConfirmationCode(newCode);
  };

  const handleKeyPress = (e, index) => {
    // Handle backspace to move to the previous input
    if (e.key === "Backspace" && index > 0) {
      inputRefs[index - 1].current.focus();
    }
  };

  const handleCodeComplete = () => {
    const code = confirmationCode.join("");
    onSubmit(code);
  };

  return (
    <div className="hero min-h-screen bg-base-200">
      <div className="hero-content flex-col lg:flex-col flex justify-center">
      <h2 className="text-center text-4xl font-bold mb-4">
        Enter Confirmation Code
      </h2>
        <div className="card shrink-0 w-full max-w-sm shadow-2xl bg-base-100">
          <div className="card-body flex flex-row">
            {confirmationCode.map((value, index) => (
              <input
                key={index}
                type="text"
                value={value}
                onChange={(e) => handleCodeChange(index, e.target.value)}
                onKeyPress={(e) => handleKeyPress(e, index)}
                maxLength="1"
                className="w-12 h-12 text-center text-2xl border border-gray-300 rounded"
                ref={inputRefs[index]}
              />
            ))}
          </div>
        </div>
      <button
        onClick={handleCodeComplete}
        className="mt-4 px-4 py-2 btn btn-primary"
      >
        Submit
      </button>
      </div>
    </div>
  );
};

export default ConfirmationCodeField;
