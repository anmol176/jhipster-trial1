import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompanyUserGroup from './company-user-group';
import CompanyUserGroupDetail from './company-user-group-detail';
import CompanyUserGroupUpdate from './company-user-group-update';
import CompanyUserGroupDeleteDialog from './company-user-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompanyUserGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompanyUserGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompanyUserGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompanyUserGroup} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompanyUserGroupDeleteDialog} />
  </>
);

export default Routes;
