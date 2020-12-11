import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompanyEntity from './company-entity';
import CompanyEntityDetail from './company-entity-detail';
import CompanyEntityUpdate from './company-entity-update';
import CompanyEntityDeleteDialog from './company-entity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompanyEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompanyEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompanyEntityDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompanyEntity} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompanyEntityDeleteDialog} />
  </>
);

export default Routes;
