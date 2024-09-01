import React, { useContext, useState } from "react";

const UserContext = React.createContext();

export const UserProvider = ({ children }) => {
  const [authenticated, setAuthenticated] = useState(false);
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [avatar, setAvatar] = useState("");
  const [token, setToken] = useState("");
  const [userObject, setUserObject] = useState(null);
  return (
    <UserContext.Provider value={{userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken}}>
      {children}
    </UserContext.Provider>
  );
};

export const useUser = () => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error("useUser must be used within a UserProvider");
  }
  return context;
};
