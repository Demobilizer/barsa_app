import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pedido-cabecera.reducer';
import { IPedidoCabecera } from 'app/shared/model/pedido-cabecera.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPedidoCabeceraDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PedidoCabeceraDetail = (props: IPedidoCabeceraDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { pedidoCabeceraEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="barsaAppApp.pedidoCabecera.detail.title">PedidoCabecera</Translate> [<b>{pedidoCabeceraEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fechaCreacion">
              <Translate contentKey="barsaAppApp.pedidoCabecera.fechaCreacion">Fecha Creacion</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={pedidoCabeceraEntity.fechaCreacion} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="fechaEntrega">
              <Translate contentKey="barsaAppApp.pedidoCabecera.fechaEntrega">Fecha Entrega</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={pedidoCabeceraEntity.fechaEntrega} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="pedidoNumero">
              <Translate contentKey="barsaAppApp.pedidoCabecera.pedidoNumero">Pedido Numero</Translate>
            </span>
          </dt>
          <dd>{pedidoCabeceraEntity.pedidoNumero}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="barsaAppApp.pedidoCabecera.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{pedidoCabeceraEntity.descripcion}</dd>
          <dt>
            <span id="facturado">
              <Translate contentKey="barsaAppApp.pedidoCabecera.facturado">Facturado</Translate>
            </span>
          </dt>
          <dd>{pedidoCabeceraEntity.facturado ? 'true' : 'false'}</dd>
          <dt>
            <span id="entregado">
              <Translate contentKey="barsaAppApp.pedidoCabecera.entregado">Entregado</Translate>
            </span>
          </dt>
          <dd>{pedidoCabeceraEntity.entregado ? 'true' : 'false'}</dd>
          <dt>
            <span id="totalBruto">
              <Translate contentKey="barsaAppApp.pedidoCabecera.totalBruto">Total Bruto</Translate>
            </span>
          </dt>
          <dd>{pedidoCabeceraEntity.totalBruto}</dd>
          <dt>
            <span id="totalIva">
              <Translate contentKey="barsaAppApp.pedidoCabecera.totalIva">Total Iva</Translate>
            </span>
          </dt>
          <dd>{pedidoCabeceraEntity.totalIva}</dd>
          <dt>
            <span id="totalImpConsumo">
              <Translate contentKey="barsaAppApp.pedidoCabecera.totalImpConsumo">Total Imp Consumo</Translate>
            </span>
          </dt>
          <dd>{pedidoCabeceraEntity.totalImpConsumo}</dd>
          <dt>
            <span id="total">
              <Translate contentKey="barsaAppApp.pedidoCabecera.total">Total</Translate>
            </span>
          </dt>
          <dd>{pedidoCabeceraEntity.total}</dd>
          <dt>
            <Translate contentKey="barsaAppApp.pedidoCabecera.clienteNo">Cliente No</Translate>
          </dt>
          <dd>{pedidoCabeceraEntity.clienteNo ? pedidoCabeceraEntity.clienteNo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pedido-cabecera" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pedido-cabecera/${pedidoCabeceraEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ pedidoCabecera }: IRootState) => ({
  pedidoCabeceraEntity: pedidoCabecera.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PedidoCabeceraDetail);
