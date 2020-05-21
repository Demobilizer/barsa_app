package com.barsa.service;

import com.barsa.domain.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Producto}.
 */
public interface ProductoService {

    /**
     * Save a producto.
     *
     * @param producto the entity to save.
     * @return the persisted entity.
     */
    Producto save(Producto producto);

    /**
     * Get all the productos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Producto> findAll(Pageable pageable);

    /**
     * Get the "id" producto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Producto> findOne(Long id);

    /**
     * Delete the "id" producto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
