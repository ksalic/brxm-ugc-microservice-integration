import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserGeneratedContent from './user-generated-content';
import UserGeneratedContentDetail from './user-generated-content-detail';
import UserGeneratedContentUpdate from './user-generated-content-update';
import UserGeneratedContentDeleteDialog from './user-generated-content-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserGeneratedContentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserGeneratedContentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserGeneratedContentDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserGeneratedContent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserGeneratedContentDeleteDialog} />
  </>
);

export default Routes;
