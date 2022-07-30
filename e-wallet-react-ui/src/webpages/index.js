import React from "react";
import {
  Routes,
  Switch,
  Route,
  Link,
} from "react-router-dom";
import Home from "./home";
import User from "./user";
const Webpages = () => {
    console.log('here Webpages component');
  return (
      <>
      <Routes>
        <Route exact path="/" element={<Home />} />
        <Route path="/user/:id" element={<User/>} />
      </Routes>
    </>
  );
};
export default Webpages;
