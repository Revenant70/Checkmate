import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

export default function ProfileIcon() {
  const navigate = new useNavigate();

  const profilePage = () => {
    navigate("/profile/profilepage");
  };

  function logoutUser() {
        localStorage.removeItem("JWT");
        navigate("/auth");
  }

  return (
    <div className="mr-2">
      <div className="dropdown dropdown-top cursor-pointer">
        <div tabIndex={0} role="button" className="btn">
          <FontAwesomeIcon size="lg" icon={faUser} />
        </div>
        <ul
          tabIndex={0}
          className="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box w-52"
        >
          <li>
            <a onClick={profilePage}>Profile Page</a>
          </li>
          <li>
            <a onClick={logoutUser}>Logout</a>
          </li>
        </ul>
      </div>
    </div>
  );
}
