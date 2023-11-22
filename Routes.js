// src/Routes.js

import React from 'react';
import { Route, Switch } from 'react-router-dom';
import Home from './Home'; // Create a Home component (we'll create this shortly)
import DBMS from './DBMS'; // Create a DBMS component
import DDLCommands from './DDLCommands'; // Create a DDLCommands component

const Routes = () => (
  <Switch>
    <Route exact path="/" component={Home} />
    <Route path="/dbms" component={DBMS} />
    <Route path="/ddl-commands" component={DDLCommands} />
  </Switch>
);

export default Routes;
