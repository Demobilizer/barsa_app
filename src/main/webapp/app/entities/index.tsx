import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Cliente from './cliente';
import Producto from './producto';
import ListaPrecios from './lista-precios';
import PedidoCabecera from './pedido-cabecera';
import PedidoDetalle from './pedido-detalle';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}cliente`} component={Cliente} />
      <ErrorBoundaryRoute path={`${match.url}producto`} component={Producto} />
      <ErrorBoundaryRoute path={`${match.url}lista-precios`} component={ListaPrecios} />
      <ErrorBoundaryRoute path={`${match.url}pedido-cabecera`} component={PedidoCabecera} />
      <ErrorBoundaryRoute path={`${match.url}pedido-detalle`} component={PedidoDetalle} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
