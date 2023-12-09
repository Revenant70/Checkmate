import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { AnimatePresence } from "framer-motion";
import { useState } from "react";
import axios from "axios";

export default function UserForgotPassword() {
  const emailRegex = new RegExp("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
  const [currentBtnMessage, setCurrentBtnMessage] = useState("Send code");
  const [email, setEmail] = useState("");
  const [currentValidEmail, setCurrentValidEmail] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const navigate = new useNavigate();

  function sendBackToUserAuth() {
    navigate("/auth");
  }

  function confirmationPopup(e){
    e.preventDefault();
    if(email != "" && emailRegex.test(email)) {
        setCurrentValidEmail(email);
        setIsOpen((isOpen) => !isOpen);
        setCurrentBtnMessage("Resend code");
        sendConfirmationCode(e);

        setTimeout(() => {
            setIsOpen(false);
          }, 3000);
    }
  }

  const sendConfirmationCode = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/users/send-confirmation-code", {
          email: currentValidEmail
        }
      );
      console.log(response);
      if(response.data != "" && response.status == 200){
        navigate("/auth/passwordrecovery/changepassword");
      }

    } catch (e) {
      console.log('Login failed', e.message);

    }
  };

  return (
    <div className="hero min-h-screen bg-base-200 flex justify-center align-middle flex-col">
      <div className="fixed top-10 left-10">
        <FontAwesomeIcon
          className="btn btn-sm"
          icon={faArrowLeft}
          onClick={sendBackToUserAuth}
        />
      </div>
      <div className="hero-content flex-col lg:flex-row-reverse">
        <div className="text-center lg:text-left w-5/12">
          <h1 className="text-5xl font-bold">Enter your email.</h1>
          <p className="py-6">
            Enter the email address you have assiocated with your account for a
            link to reset your password
          </p>
        </div>
        <div className="card shrink-0 w-full max-w-sm shadow-2xl bg-base-100">
          <form className="card-body" onSubmit={sendConfirmationCode}>
            <div className="form-control">
              <label className="label">
                <span className="label-text">Email</span>
              </label>
              <input
                type="email"
                placeholder="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="input input-bordered"
                required
              />
            </div>
            <div className="form-control mt-6">
              <button
                onClick={confirmationPopup}
                className="btn btn-primary"
              >
                {currentBtnMessage}
              </button>
            </div>
          </form>
        </div>
      </div>
      <AnimatePresence>
        {isOpen && (
          <motion.div
            className="card shrink-0 w-full max-w-sm"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          >
            <div className="hero-content">
              <div className="py-6">
                <p className="text-lg text-center">code was sent to your email</p>
                <p className="py-1 text-xl text-center font-bold">{currentValidEmail}</p>
              </div>
            </div>

            <div>
                <div>
                <p className="text-lg text-center">This code expires in <span className="font-bold">5 minutes</span></p>
                </div>
            </div>
          </motion.div>
          
        )}
      </AnimatePresence>
    </div>
  );
}
