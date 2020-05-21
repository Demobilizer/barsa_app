import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, ICrudGetAllAction, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './producto.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IProductoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Producto = (props: IProductoProps) => {
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));
  const [sorting, setSorting] = useState(false);

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const resetAll = () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1
    });
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p
    });
    setSorting(true);
  };

  const { productoList, match, loading } = props;
  return (
    <div>
      <h2 id="producto-heading">
        <Translate contentKey="barsaAppApp.producto.home.title">Productos</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="barsaAppApp.producto.home.createLabel">Create new Producto</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < props.links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {productoList && productoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('codigo')}>
                    <Translate contentKey="barsaAppApp.producto.codigo">Codigo</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('descripcion')}>
                    <Translate contentKey="barsaAppApp.producto.descripcion">Descripcion</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('imagen')}>
                    <Translate contentKey="barsaAppApp.producto.imagen">Imagen</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('unidad')}>
                    <Translate contentKey="barsaAppApp.producto.unidad">Unidad</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cantidad')}>
                    <Translate contentKey="barsaAppApp.producto.cantidad">Cantidad</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('precio')}>
                    <Translate contentKey="barsaAppApp.producto.precio">Precio</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('iva')}>
                    <Translate contentKey="barsaAppApp.producto.iva">Iva</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('icovalor')}>
                    <Translate contentKey="barsaAppApp.producto.icovalor">Icovalor</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {productoList.map((producto, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${producto.id}`} color="link" size="sm">
                        {producto.id}
                      </Button>
                    </td>
                    <td>{producto.codigo}</td>
                    <td>{producto.descripcion}</td>
                    <td>
                      {producto.imagen ? (
                        <div>
                          <a onClick={openFile(producto.imagenContentType, producto.imagen)}>
                            <img src={`data:${producto.imagenContentType};base64,${producto.imagen}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {producto.imagenContentType}, {byteSize(producto.imagen)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{producto.unidad}</td>
                    <td>{producto.cantidad}</td>
                    <td>{producto.precio}</td>
                    <td>{producto.iva}</td>
                    <td>{producto.icovalor}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${producto.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${producto.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${producto.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="barsaAppApp.producto.home.notFound">No Productos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ producto }: IRootState) => ({
  productoList: producto.entities,
  loading: producto.loading,
  totalItems: producto.totalItems,
  links: producto.links,
  entity: producto.entity,
  updateSuccess: producto.updateSuccess
});

const mapDispatchToProps = {
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Producto);
