import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Region from './region';
import Country from './country';
import Location from './location';
import CompanyGroup from './company-group';
import CompanyEntity from './company-entity';
import CompanyEntityAccount from './company-entity-account';
import Resources from './resources';
import ResourceAction from './resource-action';
import ResourceActionGroup from './resource-action-group';
import CompanyUser from './company-user';
import CompanyUserGroup from './company-user-group';
import ServiceEnrollment from './service-enrollment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}region`} component={Region} />
      <ErrorBoundaryRoute path={`${match.url}country`} component={Country} />
      <ErrorBoundaryRoute path={`${match.url}location`} component={Location} />
      <ErrorBoundaryRoute path={`${match.url}company-group`} component={CompanyGroup} />
      <ErrorBoundaryRoute path={`${match.url}company-entity`} component={CompanyEntity} />
      <ErrorBoundaryRoute path={`${match.url}company-entity-account`} component={CompanyEntityAccount} />
      <ErrorBoundaryRoute path={`${match.url}resources`} component={Resources} />
      <ErrorBoundaryRoute path={`${match.url}resource-action`} component={ResourceAction} />
      <ErrorBoundaryRoute path={`${match.url}resource-action-group`} component={ResourceActionGroup} />
      <ErrorBoundaryRoute path={`${match.url}company-user`} component={CompanyUser} />
      <ErrorBoundaryRoute path={`${match.url}company-user-group`} component={CompanyUserGroup} />
      <ErrorBoundaryRoute path={`${match.url}service-enrollment`} component={ServiceEnrollment} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
