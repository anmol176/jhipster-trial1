import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ResourceAction from './resource-action';
import ResourceActionDetail from './resource-action-detail';
import ResourceActionUpdate from './resource-action-update';
import ResourceActionDeleteDialog from './resource-action-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResourceActionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResourceActionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResourceActionDetail} />
      <ErrorBoundaryRoute path={match.url} component={ResourceAction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResourceActionDeleteDialog} />
  </>
);

export default Routes;
