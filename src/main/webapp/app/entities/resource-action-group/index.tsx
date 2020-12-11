import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ResourceActionGroup from './resource-action-group';
import ResourceActionGroupDetail from './resource-action-group-detail';
import ResourceActionGroupUpdate from './resource-action-group-update';
import ResourceActionGroupDeleteDialog from './resource-action-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResourceActionGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResourceActionGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResourceActionGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={ResourceActionGroup} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResourceActionGroupDeleteDialog} />
  </>
);

export default Routes;
