import { useNavigate } from "react-router-dom";
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

export default function ChangeUserPassword(){
  const navigate = new useNavigate();
  const { userId, token } = useParams();

  useEffect(() => {
    // Now you can use userId and token in your logic
    console.log('User ID:', userId);
    console.log('Token:', token);

    // Add your password reset logic here
    // Send a request to your backend with the userId and token
    // to verify and reset the password
  }, [userId, token]);
    

    return (
        <>
           <div>
              
           </div>
        </>
    )
}