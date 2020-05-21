package com.barsa.service;

import com.barsa.domain.PedidoDetalle;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PedidoDetalle}.
 */
public interface PedidoDetalleService {

    /**
     * Save a pedidoDetalle.
     *
     * @param pedidoDetalle the entity to save.
     * @return the persisted entity.
     */
    PedidoDetalle save(PedidoDetalle pedidoDetalle);

    /**
     * Get all the pedidoDetalles.
     *
     * @return the list of entities.
     */
    List<PedidoDetalle> findAll();

    /**
     * Get the "id" pedidoDetalle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PedidoDetalle> findOne(Long id);

    /**
     * Delete the "id" pedidoDetalle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<PedidoDetalle> findAllByPedidoNumero(Integer pedidoNo);
}
