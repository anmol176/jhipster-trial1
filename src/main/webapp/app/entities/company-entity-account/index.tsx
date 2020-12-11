import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompanyEntityAccount from './company-entity-account';
import CompanyEntityAccountDetail from './company-entity-account-detail';
import CompanyEntityAccountUpdate from './company-entity-account-update';
import CompanyEntityAccountDeleteDialog from './company-entity-account-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompanyEntityAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompanyEntityAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompanyEntityAccountDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompanyEntityAccount} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompanyEntityAccountDeleteDialog} />
  </>
);

export default Routes;
