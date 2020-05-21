import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPedidoCabecera } from 'app/shared/model/pedido-cabecera.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './pedido-cabecera.reducer';

export interface IPedidoCabeceraDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PedidoCabeceraDeleteDialog = (props: IPedidoCabeceraDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/pedido-cabecera');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.pedidoCabeceraEntity.id);
  };

  const { pedidoCabeceraEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="barsaAppApp.pedidoCabecera.delete.question">
        <Translate contentKey="barsaAppApp.pedidoCabecera.delete.question" interpolate={{ id: pedidoCabeceraEntity.id }}>
          Are you sure you want to delete this PedidoCabecera?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-pedidoCabecera" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ pedidoCabecera }: IRootState) => ({
  pedidoCabeceraEntity: pedidoCabecera.entity,
  updateSuccess: pedidoCabecera.updateSuccess
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PedidoCabeceraDeleteDialog);
