import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import axios from "axios";

export default function UserProfile() {
  const [deleteConfirmation, setDeleteConfirmation] = useState(false);
  const [feedbackMessage, setFeedbackMessage] = useState("");

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [firstname, setFirstname] = useState("");
  const [lastname, setLastname] = useState("");

  const editUserProfile = async (e) => {
    e.preventDefault();

    // Check if password and confirm password match
    if (password !== confirmPassword) {
      setFeedbackMessage("Password and confirm password do not match");
      return;
    }

    try {
      const token = localStorage.getItem("JWT");
      const response = await axios.put(
        "http://localhost:8080/api/users/edituserprofile",
        {
          username: username,
          password: password,
          firstname: firstname,
          lastname: lastname,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 200) {
        setFeedbackMessage("Profile updated successfully");
        navigate("/auth");
      } else {
        setFeedbackMessage("Unexpected response status: " + response.status);
      }
    } catch (error) {
      setFeedbackMessage("Error during profile update. Please try again.");
    }
  };

  const handleDeleteClick = () => {
    setDeleteConfirmation(true);
  };

  const handleCancelDelete = () => {
    setDeleteConfirmation(false);
  };

  const handleConfirmDelete = async () => {
    try {
      // Add logic to delete the profile (e.g., API request)
      setFeedbackMessage("Profile deleted!");
      // Optionally, you can redirect the user or perform other actions after deletion
    } catch (error) {
      setFeedbackMessage("Error deleting profile: " + error.message);
    } finally {
      setDeleteConfirmation(false);
    }
  };

  const navigate = useNavigate();

  function sendBackToHome() {
    navigate("/home");
  }

  useEffect(() => {
    // Clear the feedback message after a short delay (e.g., 5 seconds)
    const feedbackTimer = setTimeout(() => {
      setFeedbackMessage("");
    }, 5000);

    return () => {
      clearTimeout(feedbackTimer);
    };
  }, [feedbackMessage]);

  return (
    <>
      <div className="fixed top-10 left-10">
        <FontAwesomeIcon
          className="btn btn-sm"
          icon={faArrowLeft}
          onClick={sendBackToHome}
        />
      </div>
      <div
        className="min-h-screen flex items-center justify-center"
      >
        <motion.div
        className="bg-base-200 drop-shadow-lg p-8 rounded-lg w-96"
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        exit={{ opacity: 0, y: 20 }}
        transition={{ duration: 0.3, ease: "easeInOut" }}
      >
          <h2 className="text-2xl font-semibold mb-4 text-center">
            Edit Profile
          </h2>

          <form onSubmit={editUserProfile}>
            <div className="mb-4">
              <label htmlFor="username" className="label">
                Username
              </label>
              <input
                type="text"
                id="username"
                name="username"
                placeholder="username"
                className="input input-bordered mt-1 p-2 rounded-md w-full"
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>

            <div className="mb-4">
              <label htmlFor="password" className="label">
                Password:
              </label>
              <input
                type="password"
                id="password"
                name="password"
                placeholder="password"
                className="input input-bordered mt-1 p-2 rounded-md w-full"
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>

            <div className="mb-4">
              <label htmlFor="confirmPassword" className="label">
                Confirm password:
              </label>
              <input
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                placeholder="password"
                className="input input-bordered mt-1 p-2 rounded-md w-full"
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
            </div>

            <div className="mb-4">
              <label htmlFor="firstName" className="label">
                First Name
              </label>
              <input
                type="text"
                id="firstName"
                name="firstName"
                placeholder="john"
                className="input input-bordered mt-1 p-2 rounded-md w-full"
                onChange={(e) => setFirstname(e.target.value)}
              />
            </div>

            <div className="mb-4">
              <label htmlFor="lastName" className="label">
                Last Name
              </label>
              <input
                type="text"
                id="lastName"
                name="lastName"
                placeholder="doe"
                className="input input-bordered mt-1 p-2 rounded-md w-full"
                onChange={(e) => setLastname(e.target.value)}
              />
            </div>

            <button
              type="submit"
              className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 w-full"
            >
              Save Changes
            </button>
          </form>

          {/* Display feedback message with animation */}
          <AnimatePresence>
            {feedbackMessage && (
              <motion.div
                key="feedback"
                className="mt-4 text-red-500"
                initial={{ opacity: 0, y: -20 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -20 }}
              >
                {feedbackMessage}
              </motion.div>
            )}
          </AnimatePresence>

          <div className="mt-4">
            <button
              onClick={handleDeleteClick}
              className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 w-full"
            >
              Delete Profile
            </button>
            <AnimatePresence>
              {deleteConfirmation && (
                <motion.div
                  key="deleteConfirmation"
                  initial={{ opacity: 0, y: -20 }}
                  animate={{ opacity: 1, y: 0 }}
                  exit={{ opacity: 0, y: -20 }}
                >
                  <p className="mb-2">
                    Are you sure you want to delete your profile?
                  </p>
                  <button
                    onClick={handleConfirmDelete}
                    className="bg-red-500 text-white px-4 py-2 rounded-md mr-2 hover:bg-red-600"
                  >
                    Yes, Delete
                  </button>
                  <button
                    onClick={handleCancelDelete}
                    className="bg-gray-300 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-400"
                  >
                    Cancel
                  </button>
                </motion.div>
              )}
            </AnimatePresence>
          </div>
        </motion.div>
      </div>
    </>
  );
}
