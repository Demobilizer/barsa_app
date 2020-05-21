package com.barsa.web.rest;

import com.barsa.domain.ListaPrecios;
import com.barsa.service.ListaPreciosService;
import com.barsa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.barsa.domain.ListaPrecios}.
 */
@RestController
@RequestMapping("/api")
public class ListaPreciosResource {

    private final Logger log = LoggerFactory.getLogger(ListaPreciosResource.class);

    private static final String ENTITY_NAME = "listaPrecios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListaPreciosService listaPreciosService;

    public ListaPreciosResource(ListaPreciosService listaPreciosService) {
        this.listaPreciosService = listaPreciosService;
    }

    /**
     * {@code POST  /lista-precios} : Create a new listaPrecios.
     *
     * @param listaPrecios the listaPrecios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listaPrecios, or with status {@code 400 (Bad Request)} if the listaPrecios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lista-precios")
    public ResponseEntity<ListaPrecios> createListaPrecios(@RequestBody ListaPrecios listaPrecios) throws URISyntaxException {
        log.debug("REST request to save ListaPrecios : {}", listaPrecios);
        if (listaPrecios.getId() != null) {
            throw new BadRequestAlertException("A new listaPrecios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListaPrecios result = listaPreciosService.save(listaPrecios);
        return ResponseEntity.created(new URI("/api/lista-precios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lista-precios} : Updates an existing listaPrecios.
     *
     * @param listaPrecios the listaPrecios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listaPrecios,
     * or with status {@code 400 (Bad Request)} if the listaPrecios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listaPrecios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lista-precios")
    public ResponseEntity<ListaPrecios> updateListaPrecios(@RequestBody ListaPrecios listaPrecios) throws URISyntaxException {
        log.debug("REST request to update ListaPrecios : {}", listaPrecios);
        if (listaPrecios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListaPrecios result = listaPreciosService.save(listaPrecios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listaPrecios.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lista-precios} : get all the listaPrecios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listaPrecios in body.
     */
    @GetMapping("/lista-precios")
    public List<ListaPrecios> getAllListaPrecios() {
        log.debug("REST request to get all ListaPrecios");
        return listaPreciosService.findAll();
    }

    /**
     * {@code GET  /lista-precios/:id} : get the "id" listaPrecios.
     *
     * @param id the id of the listaPrecios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listaPrecios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lista-precios/{id}")
    public ResponseEntity<ListaPrecios> getListaPrecios(@PathVariable Long id) {
        log.debug("REST request to get ListaPrecios : {}", id);
        Optional<ListaPrecios> listaPrecios = listaPreciosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listaPrecios);
    }

    /**
     * {@code DELETE  /lista-precios/:id} : delete the "id" listaPrecios.
     *
     * @param id the id of the listaPrecios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lista-precios/{id}")
    public ResponseEntity<Void> deleteListaPrecios(@PathVariable Long id) {
        log.debug("REST request to delete ListaPrecios : {}", id);
        listaPreciosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
