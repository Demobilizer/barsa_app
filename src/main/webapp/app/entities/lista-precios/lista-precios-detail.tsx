import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './lista-precios.reducer';
import { IListaPrecios } from 'app/shared/model/lista-precios.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IListaPreciosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ListaPreciosDetail = (props: IListaPreciosDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { listaPreciosEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="barsaAppApp.listaPrecios.detail.title">ListaPrecios</Translate> [<b>{listaPreciosEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descripcion">
              <Translate contentKey="barsaAppApp.listaPrecios.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{listaPreciosEntity.descripcion}</dd>
          <dt>
            <span id="porcentaje">
              <Translate contentKey="barsaAppApp.listaPrecios.porcentaje">Porcentaje</Translate>
            </span>
          </dt>
          <dd>{listaPreciosEntity.porcentaje}</dd>
          <dt>
            <span id="valor">
              <Translate contentKey="barsaAppApp.listaPrecios.valor">Valor</Translate>
            </span>
          </dt>
          <dd>{listaPreciosEntity.valor}</dd>
        </dl>
        <Button tag={Link} to="/lista-precios" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lista-precios/${listaPreciosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ listaPrecios }: IRootState) => ({
  listaPreciosEntity: listaPrecios.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ListaPreciosDetail);
