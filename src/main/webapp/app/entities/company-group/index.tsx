import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompanyGroup from './company-group';
import CompanyGroupDetail from './company-group-detail';
import CompanyGroupUpdate from './company-group-update';
import CompanyGroupDeleteDialog from './company-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompanyGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompanyGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompanyGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompanyGroup} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompanyGroupDeleteDialog} />
  </>
);

export default Routes;
