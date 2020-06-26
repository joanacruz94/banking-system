import React from "react";
import { Switch, Route, BrowserRouter } from "react-router-dom";
import SignIn from "./components/home";
import SignUp from "./components/auth";

const App = () => {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/" exact>
          <SignIn />
        </Route>
        <Route path="/sign-up" exact>
          <SignUp />
        </Route>
      </Switch>
    </BrowserRouter>
  );
};

export default App;
