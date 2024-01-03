import { useNavigate } from "react-router-dom";
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";

export default function ChangeUserPassword() {
  const navigate = useNavigate();
  const { userId, token } = useParams();

  const [password, setPassword] = useState("");
  const [confirmationPassword, setConfirmationPassword] = useState("");
  const [passwordsMatch, setPasswordsMatch] = useState(true);

  useEffect(() => {
    console.log('User ID:', userId);
    console.log('Token:', token);

  }, [userId, token]);

  function sendBackToPasswordRecovery() {
    navigate("/auth/passwordrecovery");
  }

  function passwordCheck() {
    setPasswordsMatch(password === confirmationPassword);
  }

  useEffect(() => {
    const authenticateToken = async () => {
      try {
        const response = await axios.post(
          'http://localhost:8080/api/users/authenticate-token',
          {
            userId,
            token,
          }
        );

      } catch (error) {
        navigate("/auth/passwordrecovery")
        console.error(error.message);
      }
    };

    authenticateToken();
  }, [userId, token]);

  const resetPassword = async (e) => {
    e.preventDefault();
    console.log(password);
    try {
      const response = await axios.post(
        'http://localhost:8080/api/users/reset-password',
        {
          password
        }
      );
        if(response.status == 200) {
          navigate("/auth")
        }
    } catch (error) {
      console.error(error.message);
    }
  };

  return (
    <div className="hero min-h-screen bg-base-200 flex justify-center align-middle flex-col">
      <div className="fixed top-10 left-10">
        <FontAwesomeIcon
          className="btn btn-sm"
          icon={faArrowLeft}
          onClick={sendBackToPasswordRecovery}
        />
      </div>
      <div className="hero-content flex-col lg:flex-row-reverse">
        <div className="text-center lg:text-left w-5/12">
          <h1 className="text-5xl font-bold">Enter your new password</h1>
          <p className="py-6">
            Enter a new password then confirm the password
          </p>
        </div>
        <div className="card shrink-0 w-full max-w-sm shadow-2xl bg-base-100">
          <form className="card-body" onSubmit={resetPassword}>
            <div className="form-control">
              <label className="label">
                <span className="label-text">Password</span>
              </label>
              <input
                type="password"
                placeholder="password"
                className={`input input-bordered ${passwordsMatch ? '' : 'input-error'}`}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <div className="form-control">
              <label className="label">
                <span className="label-text">Confirm Password</span>
              </label>
              <input
                type="password"
                placeholder="confirm password"
                className={`input input-bordered ${passwordsMatch ? '' : 'input-error'}`}
                value={confirmationPassword}
                onChange={(e) => setConfirmationPassword(e.target.value)}
                onBlur={passwordCheck}
                required
              />
            </div>
            {!passwordsMatch && (
              <p className="text-error mt-2">Passwords do not match.</p>
            )}
            <div className="form-control mt-6">
              <button
                type="submit"
                className="btn btn-primary"
              >
                {"Reset Password"}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
