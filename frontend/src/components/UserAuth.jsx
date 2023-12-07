import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function UserAuth() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = new useNavigate();

  const authenticateUser = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/login", {
          email,
          password,
        }
      );
      console.log(response);
      if(response.data != ""){
        navigate("/home");
      }

    } catch (e) {
      console.log('Login failed', e.message);

    }
  };

  function sendToPassRecovery(){
    navigate("/auth/passwordrecovery")
  }

  return (
    <div className="hero min-h-screen bg-base-200">
      <div className="hero-content flex-col lg:flex-row-reverse">
        <div className="text-center lg:text-left w-5/12">
          <h1 className="text-5xl font-bold">Login now!</h1>
          <p className="py-6">
            Provident cupiditate voluptatem et in. Quaerat fugiat ut assumenda
            excepturi exercitationem quasi. In deleniti eaque aut repudiandae et
            a id nisi.
          </p>
        </div>
        <div className="card shrink-0 w-full max-w-sm shadow-2xl bg-base-100">
          <form className="card-body" onSubmit={authenticateUser}>
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
            <div className="form-control">
              <label className="label">
                <span className="label-text">Password</span>
              </label>
              <input
                type="password"
                placeholder="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="input input-bordered"
                required
              />
              <label className="label">
                <a
                  onClick={sendToPassRecovery}
                  className="label-text-alt link link-hover"
                >
                  Forgot password?
                </a>
              </label>
            </div>
            <div className="form-control mt-6">
              <button className="btn btn-primary">Login</button>
            </div>
            <div className="flex justify- align-bottom">
              <label className="label label-text-alt">
                Don&apos;t have an account?
                <a
                  href="/api/v1/users/login/signup"
                  className="link link-hover pl-2 flex just"
                >
                  Sign up now!
                </a>
              </label>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
