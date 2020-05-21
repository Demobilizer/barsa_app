import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICliente } from 'app/shared/model/cliente.model';
import { getEntities as getClientes } from 'app/entities/cliente/cliente.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pedido-cabecera.reducer';
import { IPedidoCabecera } from 'app/shared/model/pedido-cabecera.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPedidoCabeceraUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PedidoCabeceraUpdate = (props: IPedidoCabeceraUpdateProps) => {
  const [clienteNoId, setClienteNoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { pedidoCabeceraEntity, clientes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pedido-cabecera');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClientes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.fechaCreacion = convertDateTimeToServer(values.fechaCreacion);
    values.fechaEntrega = convertDateTimeToServer(values.fechaEntrega);

    if (errors.length === 0) {
      const entity = {
        ...pedidoCabeceraEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="barsaAppApp.pedidoCabecera.home.createOrEditLabel">
            <Translate contentKey="barsaAppApp.pedidoCabecera.home.createOrEditLabel">Create or edit a PedidoCabecera</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pedidoCabeceraEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pedido-cabecera-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="pedido-cabecera-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fechaCreacionLabel" for="pedido-cabecera-fechaCreacion">
                  <Translate contentKey="barsaAppApp.pedidoCabecera.fechaCreacion">Fecha Creacion</Translate>
                </Label>
                <AvInput
                  id="pedido-cabecera-fechaCreacion"
                  type="datetime-local"
                  className="form-control"
                  name="fechaCreacion"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.pedidoCabeceraEntity.fechaCreacion)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechaEntregaLabel" for="pedido-cabecera-fechaEntrega">
                  <Translate contentKey="barsaAppApp.pedidoCabecera.fechaEntrega">Fecha Entrega</Translate>
                </Label>
                <AvInput
                  id="pedido-cabecera-fechaEntrega"
                  type="datetime-local"
                  className="form-control"
                  name="fechaEntrega"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.pedidoCabeceraEntity.fechaEntrega)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="pedidoNumeroLabel" for="pedido-cabecera-pedidoNumero">
                  <Translate contentKey="barsaAppApp.pedidoCabecera.pedidoNumero">Pedido Numero</Translate>
                </Label>
                <AvField id="pedido-cabecera-pedidoNumero" type="string" className="form-control" name="pedidoNumero" />
              </AvGroup>
              <AvGroup>
                <Label id="descripcionLabel" for="pedido-cabecera-descripcion">
                  <Translate contentKey="barsaAppApp.pedidoCabecera.descripcion">Descripcion</Translate>
                </Label>
                <AvField id="pedido-cabecera-descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup check>
                <Label id="facturadoLabel">
                  <AvInput id="pedido-cabecera-facturado" type="checkbox" className="form-check-input" name="facturado" />
                  <Translate contentKey="barsaAppApp.pedidoCabecera.facturado">Facturado</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="entregadoLabel">
                  <AvInput id="pedido-cabecera-entregado" type="checkbox" className="form-check-input" name="entregado" />
                  <Translate contentKey="barsaAppApp.pedidoCabecera.entregado">Entregado</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="totalBrutoLabel" for="pedido-cabecera-totalBruto">
                  <Translate contentKey="barsaAppApp.pedidoCabecera.totalBruto">Total Bruto</Translate>
                </Label>
                <AvField id="pedido-cabecera-totalBruto" type="text" name="totalBruto" />
              </AvGroup>
              <AvGroup>
                <Label id="totalIvaLabel" for="pedido-cabecera-totalIva">
                  <Translate contentKey="barsaAppApp.pedidoCabecera.totalIva">Total Iva</Translate>
                </Label>
                <AvField id="pedido-cabecera-totalIva" type="text" name="totalIva" />
              </AvGroup>
              <AvGroup>
                <Label id="totalImpConsumoLabel" for="pedido-cabecera-totalImpConsumo">
                  <Translate contentKey="barsaAppApp.pedidoCabecera.totalImpConsumo">Total Imp Consumo</Translate>
                </Label>
                <AvField id="pedido-cabecera-totalImpConsumo" type="text" name="totalImpConsumo" />
              </AvGroup>
              <AvGroup>
                <Label id="totalLabel" for="pedido-cabecera-total">
                  <Translate contentKey="barsaAppApp.pedidoCabecera.total">Total</Translate>
                </Label>
                <AvField id="pedido-cabecera-total" type="text" name="total" />
              </AvGroup>
              <AvGroup>
                <Label for="pedido-cabecera-clienteNo">
                  <Translate contentKey="barsaAppApp.pedidoCabecera.clienteNo">Cliente No</Translate>
                </Label>
                <AvInput id="pedido-cabecera-clienteNo" type="select" className="form-control" name="clienteNo.id">
                  <option value="" key="0" />
                  {clientes
                    ? clientes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pedido-cabecera" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  clientes: storeState.cliente.entities,
  pedidoCabeceraEntity: storeState.pedidoCabecera.entity,
  loading: storeState.pedidoCabecera.loading,
  updating: storeState.pedidoCabecera.updating,
  updateSuccess: storeState.pedidoCabecera.updateSuccess
});

const mapDispatchToProps = {
  getClientes,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PedidoCabeceraUpdate);
