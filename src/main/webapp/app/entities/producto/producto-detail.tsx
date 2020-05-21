import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './producto.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductoDetail = (props: IProductoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="barsaAppApp.producto.detail.title">Producto</Translate> [<b>{productoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="codigo">
              <Translate contentKey="barsaAppApp.producto.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{productoEntity.codigo}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="barsaAppApp.producto.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{productoEntity.descripcion}</dd>
          <dt>
            <span id="imagen">
              <Translate contentKey="barsaAppApp.producto.imagen">Imagen</Translate>
            </span>
          </dt>
          <dd>
            {productoEntity.imagen ? (
              <div>
                <a onClick={openFile(productoEntity.imagenContentType, productoEntity.imagen)}>
                  <img src={`data:${productoEntity.imagenContentType};base64,${productoEntity.imagen}`} style={{ maxHeight: '30px' }} />
                </a>
                <span>
                  {productoEntity.imagenContentType}, {byteSize(productoEntity.imagen)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="unidad">
              <Translate contentKey="barsaAppApp.producto.unidad">Unidad</Translate>
            </span>
          </dt>
          <dd>{productoEntity.unidad}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="barsaAppApp.producto.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{productoEntity.cantidad}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="barsaAppApp.producto.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{productoEntity.precio}</dd>
          <dt>
            <span id="iva">
              <Translate contentKey="barsaAppApp.producto.iva">Iva</Translate>
            </span>
          </dt>
          <dd>{productoEntity.iva}</dd>
          <dt>
            <span id="icovalor">
              <Translate contentKey="barsaAppApp.producto.icovalor">Icovalor</Translate>
            </span>
          </dt>
          <dd>{productoEntity.icovalor}</dd>
        </dl>
        <Button tag={Link} to="/producto" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto/${productoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ producto }: IRootState) => ({
  productoEntity: producto.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductoDetail);
