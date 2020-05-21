package com.barsa.service;

import com.barsa.domain.ListaPrecios;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ListaPrecios}.
 */
public interface ListaPreciosService {

    /**
     * Save a listaPrecios.
     *
     * @param listaPrecios the entity to save.
     * @return the persisted entity.
     */
    ListaPrecios save(ListaPrecios listaPrecios);

    /**
     * Get all the listaPrecios.
     *
     * @return the list of entities.
     */
    List<ListaPrecios> findAll();

    /**
     * Get the "id" listaPrecios.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ListaPrecios> findOne(Long id);

    /**
     * Delete the "id" listaPrecios.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
