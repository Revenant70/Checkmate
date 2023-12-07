import { useNavigate } from "react-router-dom";


export default function ChangeUserPassword(){
  const navigate = new useNavigate();
    

    function ifCorrectLLogin() {
        navigate("/home");
      }
    

    return (
        <>
           <div>
              
           </div>
        </>
    )
}