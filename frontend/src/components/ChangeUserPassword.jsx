import { useNavigate } from "react-router-dom";


export default function ChangeUserPassword(){
  const navigate = new useNavigate();
    

    function ifCorrectLogin() {
        navigate("/home");
      }
    

    return (
        <>
           <div>
              
           </div>
        </>
    )
}