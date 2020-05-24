import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPedidoCabecera } from 'app/shared/model/pedido-cabecera.model';
import { getEntities as getPedidoCabeceras } from 'app/entities/pedido-cabecera/pedido-cabecera.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pedido-detalle.reducer';
import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPedidoDetalleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PedidoDetalleUpdate = (props: IPedidoDetalleUpdateProps) => {
  const [pedidoCabeceraId, setPedidoCabeceraId] = useState('0');
  const [productoId, setProductoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { pedidoDetalleEntity, pedidoCabeceras, productos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pedido-detalle');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPedidoCabeceras();
    props.getProductos();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...pedidoDetalleEntity,
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
          <h2 id="barsaAppApp.pedidoDetalle.home.createOrEditLabel">
            <Translate contentKey="barsaAppApp.pedidoDetalle.home.createOrEditLabel">Create or edit a PedidoDetalle</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pedidoDetalleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pedido-detalle-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="pedido-detalle-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="posicionLabel" for="pedido-detalle-posicion">
                  <Translate contentKey="barsaAppApp.pedidoDetalle.posicion">Posicion</Translate>
                </Label>
                <AvField id="pedido-detalle-posicion" type="string" className="form-control" name="posicion" />
              </AvGroup>
              <AvGroup>
                <Label id="cantidadLabel" for="pedido-detalle-cantidad">
                  <Translate contentKey="barsaAppApp.pedidoDetalle.cantidad">Cantidad</Translate>
                </Label>
                <AvField id="pedido-detalle-cantidad" type="string" className="form-control" name="cantidad" />
              </AvGroup>
              <AvGroup>
                <Label id="totalLabel" for="pedido-detalle-total">
                  <Translate contentKey="barsaAppApp.pedidoDetalle.total">Total</Translate>
                </Label>
                <AvField id="pedido-detalle-total" type="text" name="total" />
              </AvGroup>
              <AvGroup>
                <Label for="pedido-detalle-pedidoCabecera">
                  <Translate contentKey="barsaAppApp.pedidoDetalle.pedidoCabecera">Pedido Cabecera</Translate>
                </Label>
                <AvInput
                  id="pedido-detalle-pedidoCabecera"
                  type="select"
                  className="form-control"
                  name="pedidoCabecera.id"
                  value={isNew ? pedidoCabeceras[0] && pedidoCabeceras[0].id : pedidoDetalleEntity.pedidoCabecera.id}
                  required
                >
                  {pedidoCabeceras
                    ? pedidoCabeceras.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.pedidoNumero}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="pedido-detalle-producto">
                  <Translate contentKey="barsaAppApp.pedidoDetalle.producto">Producto</Translate>
                </Label>
                <AvInput id="pedido-detalle-producto" type="select" className="form-control" name="producto.id">
                  <option value="" key="0" />
                  {productos
                    ? productos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pedido-detalle" replace color="info">
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
  pedidoCabeceras: storeState.pedidoCabecera.entities,
  productos: storeState.producto.entities,
  pedidoDetalleEntity: storeState.pedidoDetalle.entity,
  loading: storeState.pedidoDetalle.loading,
  updating: storeState.pedidoDetalle.updating,
  updateSuccess: storeState.pedidoDetalle.updateSuccess
});

const mapDispatchToProps = {
  getPedidoCabeceras,
  getProductos,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PedidoDetalleUpdate);
