import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PedidoDetalle from './pedido-detalle';
import PedidoDetalleDetail from './pedido-detalle-detail';
import PedidoDetalleUpdate from './pedido-detalle-update';
import PedidoDetalleDeleteDialog from './pedido-detalle-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PedidoDetalleDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PedidoDetalleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PedidoDetalleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PedidoDetalleDetail} />
      <ErrorBoundaryRoute path={match.url} component={PedidoDetalle} />
    </Switch>
  </>
);

export default Routes;
