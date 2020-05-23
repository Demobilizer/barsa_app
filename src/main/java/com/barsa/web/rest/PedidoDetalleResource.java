package com.barsa.web.rest;

import com.barsa.domain.PedidoDetalle;
import com.barsa.service.PedidoDetalleService;
import com.barsa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link PedidoDetalle}.
 */
@RestController
@RequestMapping("/api")
public class PedidoDetalleResource {

    private final Logger log = LoggerFactory.getLogger(PedidoDetalleResource.class);

    private static final String ENTITY_NAME = "pedidoDetalle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PedidoDetalleService pedidoDetalleService;

    public PedidoDetalleResource(@Qualifier("pedidoDetalleServiceImpl") PedidoDetalleService pedidoDetalleService) {
        this.pedidoDetalleService = pedidoDetalleService;
    }

    /**
     * {@code POST  /pedido-detalles} : Create a new pedidoDetalle.
     *
     * @param pedidoDetalle the pedidoDetalle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pedidoDetalle, or with status {@code 400 (Bad Request)} if the pedidoDetalle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pedido-detalles")
    public ResponseEntity<PedidoDetalle> createPedidoDetalle(@Valid @RequestBody PedidoDetalle pedidoDetalle) throws URISyntaxException {
        log.debug("REST request to save PedidoDetalle : {}", pedidoDetalle);
        if (pedidoDetalle.getId() != null) {
            throw new BadRequestAlertException("A new pedidoDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PedidoDetalle result = pedidoDetalleService.save(pedidoDetalle);
        return ResponseEntity.created(new URI("/api/pedido-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pedido-detalles} : Updates an existing pedidoDetalle.
     *
     * @param pedidoDetalle the pedidoDetalle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pedidoDetalle,
     * or with status {@code 400 (Bad Request)} if the pedidoDetalle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pedidoDetalle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pedido-detalles")
    public ResponseEntity<PedidoDetalle> updatePedidoDetalle(@Valid @RequestBody PedidoDetalle pedidoDetalle) throws URISyntaxException {
        log.debug("REST request to update PedidoDetalle : {}", pedidoDetalle);
        if (pedidoDetalle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PedidoDetalle result = pedidoDetalleService.save(pedidoDetalle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pedidoDetalle.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pedido-detalles} : get all the pedidoDetalles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pedidoDetalles in body.
     */
    @GetMapping("/pedido-detalles")
    public List<PedidoDetalle> getAllPedidoDetalles() {
        log.debug("REST request to get all PedidoDetalles");
        return pedidoDetalleService.findAll();
    }

    /**
     * {@code GET  /pedido-detalles/:id} : get the "id" pedidoDetalle.
     *
     * @param id the id of the pedidoDetalle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pedidoDetalle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pedido-detalles/{id}")
    public ResponseEntity<PedidoDetalle> getPedidoDetalle(@PathVariable Long id) {
        log.debug("REST request to get PedidoDetalle : {}", id);
        Optional<PedidoDetalle> pedidoDetalle = pedidoDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pedidoDetalle);
    }

    /**
     * {@code DELETE  /pedido-detalles/:id} : delete the "id" pedidoDetalle.
     *
     * @param id the id of the pedidoDetalle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pedido-detalles/{id}")
    public ResponseEntity<Void> deletePedidoDetalle(@PathVariable Long id) {
        log.debug("REST request to delete PedidoDetalle : {}", id);
        pedidoDetalleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
