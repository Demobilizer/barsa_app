import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ListaPrecios from './lista-precios';
import ListaPreciosDetail from './lista-precios-detail';
import ListaPreciosUpdate from './lista-precios-update';
import ListaPreciosDeleteDialog from './lista-precios-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ListaPreciosDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ListaPreciosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ListaPreciosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ListaPreciosDetail} />
      <ErrorBoundaryRoute path={match.url} component={ListaPrecios} />
    </Switch>
  </>
);

export default Routes;
