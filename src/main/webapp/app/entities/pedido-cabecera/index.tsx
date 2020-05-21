import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PedidoCabecera from './pedido-cabecera';
import PedidoCabeceraDetail from './pedido-cabecera-detail';
import PedidoCabeceraUpdate from './pedido-cabecera-update';
import PedidoCabeceraDeleteDialog from './pedido-cabecera-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PedidoCabeceraDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PedidoCabeceraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PedidoCabeceraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PedidoCabeceraDetail} />
      <ErrorBoundaryRoute path={match.url} component={PedidoCabecera} />
    </Switch>
  </>
);

export default Routes;
