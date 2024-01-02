import { Routes, Route, Navigate} from "react-router-dom";

function App() {
  return (
      <Routes>
        <Route path="*" element={<Navigate to="/auth" /> } />
        <Route path="/body"/>
        <Route path="/auth/passwordrecovery"/>
        <Route path="/auth/passwordrecovery/changepassword" />
        <Route path="/auth/passwordrecovery/confirmationcode" />
        <Route path="/auth/signup" />
        <Route path="/profile/profilepage" />
      </Routes>
  );
}

export default App;
