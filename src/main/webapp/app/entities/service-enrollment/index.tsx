import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceEnrollment from './service-enrollment';
import ServiceEnrollmentDetail from './service-enrollment-detail';
import ServiceEnrollmentUpdate from './service-enrollment-update';
import ServiceEnrollmentDeleteDialog from './service-enrollment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceEnrollmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceEnrollmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceEnrollmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceEnrollment} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceEnrollmentDeleteDialog} />
  </>
);

export default Routes;
