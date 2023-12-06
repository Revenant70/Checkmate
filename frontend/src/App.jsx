import { Routes, Route, Navigate} from "react-router-dom";

function App() {
  return (
      <Routes>
        <Route path="*" element={<Navigate to="/auth" /> } />
        <Route path="/body"/>
        <Route path="/auth/passwordrecovery"/>
        <Route path="/auth/passwordrecovery/changepassword" />
      </Routes> 
  );
}

export default App;
