import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './lista-precios.reducer';
import { IListaPrecios } from 'app/shared/model/lista-precios.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IListaPreciosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ListaPreciosUpdate = (props: IListaPreciosUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { listaPreciosEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/lista-precios');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
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
        ...listaPreciosEntity,
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
          <h2 id="barsaAppApp.listaPrecios.home.createOrEditLabel">
            <Translate contentKey="barsaAppApp.listaPrecios.home.createOrEditLabel">Create or edit a ListaPrecios</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : listaPreciosEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="lista-precios-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="lista-precios-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descripcionLabel" for="lista-precios-descripcion">
                  <Translate contentKey="barsaAppApp.listaPrecios.descripcion">Descripcion</Translate>
                </Label>
                <AvField id="lista-precios-descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup>
                <Label id="porcentajeLabel" for="lista-precios-porcentaje">
                  <Translate contentKey="barsaAppApp.listaPrecios.porcentaje">Porcentaje</Translate>
                </Label>
                <AvField id="lista-precios-porcentaje" type="text" name="porcentaje" />
              </AvGroup>
              <AvGroup>
                <Label id="valorLabel" for="lista-precios-valor">
                  <Translate contentKey="barsaAppApp.listaPrecios.valor">Valor</Translate>
                </Label>
                <AvField id="lista-precios-valor" type="text" name="valor" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/lista-precios" replace color="info">
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
  listaPreciosEntity: storeState.listaPrecios.entity,
  loading: storeState.listaPrecios.loading,
  updating: storeState.listaPrecios.updating,
  updateSuccess: storeState.listaPrecios.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ListaPreciosUpdate);
