package com.barsa.service.impl;

import com.barsa.service.ListaPreciosService;
import com.barsa.domain.ListaPrecios;
import com.barsa.repository.ListaPreciosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ListaPrecios}.
 */
@Service
@Transactional
public class ListaPreciosServiceImpl implements ListaPreciosService {

    private final Logger log = LoggerFactory.getLogger(ListaPreciosServiceImpl.class);

    private final ListaPreciosRepository listaPreciosRepository;

    public ListaPreciosServiceImpl(ListaPreciosRepository listaPreciosRepository) {
        this.listaPreciosRepository = listaPreciosRepository;
    }

    /**
     * Save a listaPrecios.
     *
     * @param listaPrecios the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ListaPrecios save(ListaPrecios listaPrecios) {
        log.debug("Request to save ListaPrecios : {}", listaPrecios);
        return listaPreciosRepository.save(listaPrecios);
    }

    /**
     * Get all the listaPrecios.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListaPrecios> findAll() {
        log.debug("Request to get all ListaPrecios");
        return listaPreciosRepository.findAll();
    }

    /**
     * Get one listaPrecios by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ListaPrecios> findOne(Long id) {
        log.debug("Request to get ListaPrecios : {}", id);
        return listaPreciosRepository.findById(id);
    }

    /**
     * Delete the listaPrecios by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ListaPrecios : {}", id);
        listaPreciosRepository.deleteById(id);
    }
}
