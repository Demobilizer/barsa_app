package com.barsa.web.rest;

import com.barsa.domain.PedidoCabecera;
import com.barsa.service.PedidoCabeceraService;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.barsa.domain.PedidoCabecera}.
 */
@RestController
@RequestMapping("/api")
public class PedidoCabeceraResource {

    private final Logger log = LoggerFactory.getLogger(PedidoCabeceraResource.class);

    private static final String ENTITY_NAME = "pedidoCabecera";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PedidoCabeceraService pedidoCabeceraService;

    public PedidoCabeceraResource(PedidoCabeceraService pedidoCabeceraService) {
        this.pedidoCabeceraService = pedidoCabeceraService;
    }

    /**
     * {@code POST  /pedido-cabeceras} : Create a new pedidoCabecera.
     *
     * @param pedidoCabecera the pedidoCabecera to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pedidoCabecera, or with status {@code 400 (Bad Request)} if the pedidoCabecera has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pedido-cabeceras")
    public ResponseEntity<PedidoCabecera> createPedidoCabecera(@RequestBody PedidoCabecera pedidoCabecera) throws URISyntaxException {
        log.debug("REST request to save PedidoCabecera : {}", pedidoCabecera);
        if (pedidoCabecera.getId() != null) {
            throw new BadRequestAlertException("A new pedidoCabecera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PedidoCabecera result = pedidoCabeceraService.save(pedidoCabecera);
        return ResponseEntity.created(new URI("/api/pedido-cabeceras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pedido-cabeceras} : Updates an existing pedidoCabecera.
     *
     * @param pedidoCabecera the pedidoCabecera to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pedidoCabecera,
     * or with status {@code 400 (Bad Request)} if the pedidoCabecera is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pedidoCabecera couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pedido-cabeceras")
    public ResponseEntity<PedidoCabecera> updatePedidoCabecera(@RequestBody PedidoCabecera pedidoCabecera) throws URISyntaxException {
        log.debug("REST request to update PedidoCabecera : {}", pedidoCabecera);
        if (pedidoCabecera.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PedidoCabecera result = pedidoCabeceraService.save(pedidoCabecera);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pedidoCabecera.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pedido-cabeceras} : get all the pedidoCabeceras.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pedidoCabeceras in body.
     */
    @GetMapping("/pedido-cabeceras")
    public List<PedidoCabecera> getAllPedidoCabeceras() {
        log.debug("REST request to get all PedidoCabeceras");
        return pedidoCabeceraService.findAll();
    }

    /**
     * {@code GET  /pedido-cabeceras/:id} : get the "id" pedidoCabecera.
     *
     * @param id the id of the pedidoCabecera to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pedidoCabecera, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pedido-cabecera/{id}")
    public ResponseEntity<PedidoCabecera> getPedidoCabecera(@PathVariable Long id) {
        log.debug("REST request to get PedidoCabecera : {}", id);
        Optional<PedidoCabecera> pedidoCabecera = pedidoCabeceraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pedidoCabecera);
    }

    @GetMapping("/pedido-cabeceras/{noIdentification}")
    public List<PedidoCabecera> getPedidoCabeceraByClient(@PathVariable Integer noIdentification) {
        log.debug("REST request to get all PedidoCabeceras by Client by noIdentification");
        return pedidoCabeceraService.findAllByClient(noIdentification);
    }

    @GetMapping("/pedido-cabeceras/{noIdentification}/{date}")
    public List<PedidoCabecera> getPedidoCabeceraByClienteByDate(
        @RequestParam("noIdentification") Integer noIdentification, @RequestParam("date") String date) {
        log.debug("REST request to get all PedidoCabeceras by Client by noIdentification by fechaCreacion date");

          LocalDate localDate = LocalDate.parse(date);
//        LocalDateTime localDateTime = localDate.atStartOfDay();
//        Instant fechaCreacion = localDateTime.toInstant(ZoneOffset.UTC);

        return pedidoCabeceraService.findAllByClientAndFechaCreacion(noIdentification, localDate);
    }



    /**
     * {@code DELETE  /pedido-cabeceras/:id} : delete the "id" pedidoCabecera.
     *
     * @param id the id of the pedidoCabecera to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pedido-cabeceras/{id}")
    public ResponseEntity<Void> deletePedidoCabecera(@PathVariable Long id) {
        log.debug("REST request to delete PedidoCabecera : {}", id);
        pedidoCabeceraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
