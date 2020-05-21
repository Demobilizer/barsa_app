import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cliente.reducer';
import { ICliente } from 'app/shared/model/cliente.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClienteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClienteDetail = (props: IClienteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { clienteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="barsaAppApp.cliente.detail.title">Cliente</Translate> [<b>{clienteEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tipoDoc">
              <Translate contentKey="barsaAppApp.cliente.tipoDoc">Tipo Doc</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.tipoDoc}</dd>
          <dt>
            <span id="noIdentificacion">
              <Translate contentKey="barsaAppApp.cliente.noIdentificacion">No Identificacion</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.noIdentificacion}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="barsaAppApp.cliente.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.nombre}</dd>
          <dt>
            <span id="apellido">
              <Translate contentKey="barsaAppApp.cliente.apellido">Apellido</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.apellido}</dd>
          <dt>
            <span id="direccion">
              <Translate contentKey="barsaAppApp.cliente.direccion">Direccion</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.direccion}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="barsaAppApp.cliente.telefono">Telefono</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.telefono}</dd>
          <dt>
            <span id="celular">
              <Translate contentKey="barsaAppApp.cliente.celular">Celular</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.celular}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="barsaAppApp.cliente.email">Email</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.email}</dd>
        </dl>
        <Button tag={Link} to="/cliente" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cliente/${clienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cliente }: IRootState) => ({
  clienteEntity: cliente.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClienteDetail);
