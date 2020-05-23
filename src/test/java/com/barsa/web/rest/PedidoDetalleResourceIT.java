package com.barsa.web.rest;

import com.barsa.BarsaAppApp;
import com.barsa.domain.PedidoDetalle;
import com.barsa.domain.PedidoCabecera;
import com.barsa.repository.PedidoDetalleRepository;
import com.barsa.service.PedidoDetalleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PedidoDetalleResource} REST controller.
 */
@SpringBootTest(classes = BarsaAppApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PedidoDetalleResourceIT {

    private static final Integer DEFAULT_POSICION = 1;
    private static final Integer UPDATED_POSICION = 2;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    private PedidoDetalleService pedidoDetalleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPedidoDetalleMockMvc;

    private PedidoDetalle pedidoDetalle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PedidoDetalle createEntity(EntityManager em) {
        PedidoDetalle pedidoDetalle = new PedidoDetalle()
            .posicion(DEFAULT_POSICION)
            .cantidad(DEFAULT_CANTIDAD)
            .total(DEFAULT_TOTAL);
        // Add required entity
        PedidoCabecera pedidoCabecera;
        if (TestUtil.findAll(em, PedidoCabecera.class).isEmpty()) {
            pedidoCabecera = PedidoCabeceraResourceIT.createEntity(em);
            em.persist(pedidoCabecera);
            em.flush();
        } else {
            pedidoCabecera = TestUtil.findAll(em, PedidoCabecera.class).get(0);
        }
        pedidoDetalle.setPedidoCabecera(pedidoCabecera);
        return pedidoDetalle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PedidoDetalle createUpdatedEntity(EntityManager em) {
        PedidoDetalle pedidoDetalle = new PedidoDetalle()
            .posicion(UPDATED_POSICION)
            .cantidad(UPDATED_CANTIDAD)
            .total(UPDATED_TOTAL);
        // Add required entity
        PedidoCabecera pedidoCabecera;
        if (TestUtil.findAll(em, PedidoCabecera.class).isEmpty()) {
            pedidoCabecera = PedidoCabeceraResourceIT.createUpdatedEntity(em);
            em.persist(pedidoCabecera);
            em.flush();
        } else {
            pedidoCabecera = TestUtil.findAll(em, PedidoCabecera.class).get(0);
        }
        pedidoDetalle.setPedidoCabecera(pedidoCabecera);
        return pedidoDetalle;
    }

    @BeforeEach
    public void initTest() {
        pedidoDetalle = createEntity(em);
    }

    @Test
    @Transactional
    public void createPedidoDetalle() throws Exception {
        int databaseSizeBeforeCreate = pedidoDetalleRepository.findAll().size();

        // Create the PedidoDetalle
        restPedidoDetalleMockMvc.perform(post("/api/pedido-detalles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDetalle)))
            .andExpect(status().isCreated());

        // Validate the PedidoDetalle in the database
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        PedidoDetalle testPedidoDetalle = pedidoDetalleList.get(pedidoDetalleList.size() - 1);
        assertThat(testPedidoDetalle.getPosicion()).isEqualTo(DEFAULT_POSICION);
        assertThat(testPedidoDetalle.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testPedidoDetalle.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createPedidoDetalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pedidoDetalleRepository.findAll().size();

        // Create the PedidoDetalle with an existing ID
        pedidoDetalle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoDetalleMockMvc.perform(post("/api/pedido-detalles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDetalle)))
            .andExpect(status().isBadRequest());

        // Validate the PedidoDetalle in the database
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPedidoDetalles() throws Exception {
        // Initialize the database
        pedidoDetalleRepository.saveAndFlush(pedidoDetalle);

        // Get all the pedidoDetalleList
        restPedidoDetalleMockMvc.perform(get("/api/pedido-detalles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedidoDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].posicion").value(hasItem(DEFAULT_POSICION)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getPedidoDetalle() throws Exception {
        // Initialize the database
        pedidoDetalleRepository.saveAndFlush(pedidoDetalle);

        // Get the pedidoDetalle
        restPedidoDetalleMockMvc.perform(get("/api/pedido-detalles/{id}", pedidoDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pedidoDetalle.getId().intValue()))
            .andExpect(jsonPath("$.posicion").value(DEFAULT_POSICION))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPedidoDetalle() throws Exception {
        // Get the pedidoDetalle
        restPedidoDetalleMockMvc.perform(get("/api/pedido-detalles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePedidoDetalle() throws Exception {
        // Initialize the database
        pedidoDetalleService.save(pedidoDetalle);

        int databaseSizeBeforeUpdate = pedidoDetalleRepository.findAll().size();

        // Update the pedidoDetalle
        PedidoDetalle updatedPedidoDetalle = pedidoDetalleRepository.findById(pedidoDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedPedidoDetalle are not directly saved in db
        em.detach(updatedPedidoDetalle);
        updatedPedidoDetalle
            .posicion(UPDATED_POSICION)
            .cantidad(UPDATED_CANTIDAD)
            .total(UPDATED_TOTAL);

        restPedidoDetalleMockMvc.perform(put("/api/pedido-detalles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPedidoDetalle)))
            .andExpect(status().isOk());

        // Validate the PedidoDetalle in the database
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeUpdate);
        PedidoDetalle testPedidoDetalle = pedidoDetalleList.get(pedidoDetalleList.size() - 1);
        assertThat(testPedidoDetalle.getPosicion()).isEqualTo(UPDATED_POSICION);
        assertThat(testPedidoDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testPedidoDetalle.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPedidoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = pedidoDetalleRepository.findAll().size();

        // Create the PedidoDetalle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoDetalleMockMvc.perform(put("/api/pedido-detalles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDetalle)))
            .andExpect(status().isBadRequest());

        // Validate the PedidoDetalle in the database
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePedidoDetalle() throws Exception {
        // Initialize the database
        pedidoDetalleService.save(pedidoDetalle);

        int databaseSizeBeforeDelete = pedidoDetalleRepository.findAll().size();

        // Delete the pedidoDetalle
        restPedidoDetalleMockMvc.perform(delete("/api/pedido-detalles/{id}", pedidoDetalle.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
