package com.barsa.service;

import com.barsa.domain.PedidoCabecera;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PedidoCabecera}.
 */
public interface PedidoCabeceraService {

    /**
     * Save a pedidoCabecera.
     *
     * @param pedidoCabecera the entity to save.
     * @return the persisted entity.
     */
    PedidoCabecera save(PedidoCabecera pedidoCabecera);

    /**
     * Get all the pedidoCabeceras.
     *
     * @return the list of entities.
     */
    List<PedidoCabecera> findAll();

    /**
     * Get the "id" pedidoCabecera.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PedidoCabecera> findOne(Long id);

    /**
     * Delete the "id" pedidoCabecera.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
