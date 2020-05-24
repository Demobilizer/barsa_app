import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pedido-detalle.reducer';
import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPedidoDetalleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PedidoDetalleDetail = (props: IPedidoDetalleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { pedidoDetalleEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="barsaAppApp.pedidoDetalle.detail.title">PedidoDetalle</Translate> [<b>{pedidoDetalleEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="posicion">
              <Translate contentKey="barsaAppApp.pedidoDetalle.posicion">Posicion</Translate>
            </span>
          </dt>
          <dd>{pedidoDetalleEntity.posicion}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="barsaAppApp.pedidoDetalle.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{pedidoDetalleEntity.cantidad}</dd>
          <dt>
            <span id="total">
              <Translate contentKey="barsaAppApp.pedidoDetalle.total">Total</Translate>
            </span>
          </dt>
          <dd>{pedidoDetalleEntity.total}</dd>
          <dt>
            <Translate contentKey="barsaAppApp.pedidoDetalle.pedidoCabecera">Pedido Cabecera</Translate>
          </dt>
          <dd>{pedidoDetalleEntity.pedidoCabecera ? pedidoDetalleEntity.pedidoCabecera.pedidoNumero : ''}</dd>
          <dt>
            <Translate contentKey="barsaAppApp.pedidoDetalle.producto">Producto</Translate>
          </dt>
          <dd>{pedidoDetalleEntity.producto ? pedidoDetalleEntity.producto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pedido-detalle" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pedido-detalle/${pedidoDetalleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ pedidoDetalle }: IRootState) => ({
  pedidoDetalleEntity: pedidoDetalle.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PedidoDetalleDetail);
