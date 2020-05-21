import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './cliente.reducer';
import { ICliente } from 'app/shared/model/cliente.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClienteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClienteUpdate = (props: IClienteUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { clienteEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/cliente');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...clienteEntity,
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
          <h2 id="barsaAppApp.cliente.home.createOrEditLabel">
            <Translate contentKey="barsaAppApp.cliente.home.createOrEditLabel">Create or edit a Cliente</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : clienteEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="cliente-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="cliente-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="tipoDocLabel" for="cliente-tipoDoc">
                  <Translate contentKey="barsaAppApp.cliente.tipoDoc">Tipo Doc</Translate>
                </Label>
                <AvField id="cliente-tipoDoc" type="text" name="tipoDoc" />
              </AvGroup>
              <AvGroup>
                <Label id="noIdentificacionLabel" for="cliente-noIdentificacion">
                  <Translate contentKey="barsaAppApp.cliente.noIdentificacion">No Identificacion</Translate>
                </Label>
                <AvField id="cliente-noIdentificacion" type="string" className="form-control" name="noIdentificacion" />
              </AvGroup>
              <AvGroup>
                <Label id="nombreLabel" for="cliente-nombre">
                  <Translate contentKey="barsaAppApp.cliente.nombre">Nombre</Translate>
                </Label>
                <AvField id="cliente-nombre" type="text" name="nombre" />
              </AvGroup>
              <AvGroup>
                <Label id="apellidoLabel" for="cliente-apellido">
                  <Translate contentKey="barsaAppApp.cliente.apellido">Apellido</Translate>
                </Label>
                <AvField id="cliente-apellido" type="text" name="apellido" />
              </AvGroup>
              <AvGroup>
                <Label id="direccionLabel" for="cliente-direccion">
                  <Translate contentKey="barsaAppApp.cliente.direccion">Direccion</Translate>
                </Label>
                <AvField id="cliente-direccion" type="text" name="direccion" />
              </AvGroup>
              <AvGroup>
                <Label id="telefonoLabel" for="cliente-telefono">
                  <Translate contentKey="barsaAppApp.cliente.telefono">Telefono</Translate>
                </Label>
                <AvField id="cliente-telefono" type="text" name="telefono" />
              </AvGroup>
              <AvGroup>
                <Label id="celularLabel" for="cliente-celular">
                  <Translate contentKey="barsaAppApp.cliente.celular">Celular</Translate>
                </Label>
                <AvField id="cliente-celular" type="text" name="celular" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="cliente-email">
                  <Translate contentKey="barsaAppApp.cliente.email">Email</Translate>
                </Label>
                <AvField id="cliente-email" type="text" name="email" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/cliente" replace color="info">
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
  clienteEntity: storeState.cliente.entity,
  loading: storeState.cliente.loading,
  updating: storeState.cliente.updating,
  updateSuccess: storeState.cliente.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClienteUpdate);
