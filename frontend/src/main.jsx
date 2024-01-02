import React from 'react';
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import Auth from './components/UserAuth.jsx'
import Task from "./components/Task.jsx"
import ForgotPass from './components/UserForgotPassword.jsx'
import ChangePass from './components/ChangeUserPassword.jsx'
import SignUp from './components/SignUp.jsx';
import ConfirmationCode from './components/ConfirmationCodeField.jsx';
import UserProfile from './components/UserProfile.jsx';

import './index.css'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
      
  },
  {
    path: '/auth',
    element: <Auth />,
  },
  {
    path: '/auth/passwordrecovery',
    element: <ForgotPass />,
  },
  {
    path: '/auth/passwordrecovery/changepassword',
    element: <ChangePass />,
  },
  {
    path: '/auth/passwordrecovery/confirmationcode',
    element: <ConfirmationCode />,
  },
  {
    path: '/auth/signup',
    element: <SignUp />,
  },
  {
    path: '/home',
    element: <Task />,
  },
  {
    path: '/profile/profilepage',
    element: <UserProfile />
  },

  
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
)
