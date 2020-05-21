import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './producto.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductoUpdate = (props: IProductoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { productoEntity, loading, updating } = props;

  const { imagen, imagenContentType } = productoEntity;

  const handleClose = () => {
    props.history.push('/producto');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...productoEntity,
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
          <h2 id="barsaAppApp.producto.home.createOrEditLabel">
            <Translate contentKey="barsaAppApp.producto.home.createOrEditLabel">Create or edit a Producto</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="producto-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="producto-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codigoLabel" for="producto-codigo">
                  <Translate contentKey="barsaAppApp.producto.codigo">Codigo</Translate>
                </Label>
                <AvField id="producto-codigo" type="text" name="codigo" />
              </AvGroup>
              <AvGroup>
                <Label id="descripcionLabel" for="producto-descripcion">
                  <Translate contentKey="barsaAppApp.producto.descripcion">Descripcion</Translate>
                </Label>
                <AvField id="producto-descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="imagenLabel" for="imagen">
                    <Translate contentKey="barsaAppApp.producto.imagen">Imagen</Translate>
                  </Label>
                  <br />
                  {imagen ? (
                    <div>
                      <a onClick={openFile(imagenContentType, imagen)}>
                        <img src={`data:${imagenContentType};base64,${imagen}`} style={{ maxHeight: '100px' }} />
                      </a>
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {imagenContentType}, {byteSize(imagen)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('imagen')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_imagen" type="file" onChange={onBlobChange(true, 'imagen')} accept="image/*" />
                  <AvInput type="hidden" name="imagen" value={imagen} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="unidadLabel" for="producto-unidad">
                  <Translate contentKey="barsaAppApp.producto.unidad">Unidad</Translate>
                </Label>
                <AvField id="producto-unidad" type="text" name="unidad" />
              </AvGroup>
              <AvGroup>
                <Label id="cantidadLabel" for="producto-cantidad">
                  <Translate contentKey="barsaAppApp.producto.cantidad">Cantidad</Translate>
                </Label>
                <AvField id="producto-cantidad" type="string" className="form-control" name="cantidad" />
              </AvGroup>
              <AvGroup>
                <Label id="precioLabel" for="producto-precio">
                  <Translate contentKey="barsaAppApp.producto.precio">Precio</Translate>
                </Label>
                <AvField id="producto-precio" type="text" name="precio" />
              </AvGroup>
              <AvGroup>
                <Label id="ivaLabel" for="producto-iva">
                  <Translate contentKey="barsaAppApp.producto.iva">Iva</Translate>
                </Label>
                <AvField id="producto-iva" type="text" name="iva" />
              </AvGroup>
              <AvGroup>
                <Label id="icovalorLabel" for="producto-icovalor">
                  <Translate contentKey="barsaAppApp.producto.icovalor">Icovalor</Translate>
                </Label>
                <AvField id="producto-icovalor" type="text" name="icovalor" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/producto" replace color="info">
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
  productoEntity: storeState.producto.entity,
  loading: storeState.producto.loading,
  updating: storeState.producto.updating,
  updateSuccess: storeState.producto.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductoUpdate);
