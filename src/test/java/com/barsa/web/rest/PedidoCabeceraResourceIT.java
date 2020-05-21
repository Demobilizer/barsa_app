package com.barsa.web.rest;

import com.barsa.BarsaAppApp;
import com.barsa.domain.PedidoCabecera;
import com.barsa.repository.PedidoCabeceraRepository;
import com.barsa.service.PedidoCabeceraService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PedidoCabeceraResource} REST controller.
 */
@SpringBootTest(classes = BarsaAppApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PedidoCabeceraResourceIT {

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_ENTREGA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ENTREGA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PEDIDO_NUMERO = 1;
    private static final Integer UPDATED_PEDIDO_NUMERO = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FACTURADO = false;
    private static final Boolean UPDATED_FACTURADO = true;

    private static final Boolean DEFAULT_ENTREGADO = false;
    private static final Boolean UPDATED_ENTREGADO = true;

    private static final BigDecimal DEFAULT_TOTAL_BRUTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_BRUTO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_IVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_IVA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_IMP_CONSUMO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_IMP_CONSUMO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    @Autowired
    private PedidoCabeceraRepository pedidoCabeceraRepository;

    @Autowired
    private PedidoCabeceraService pedidoCabeceraService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPedidoCabeceraMockMvc;

    private PedidoCabecera pedidoCabecera;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PedidoCabecera createEntity(EntityManager em) {
        PedidoCabecera pedidoCabecera = new PedidoCabecera()
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaEntrega(DEFAULT_FECHA_ENTREGA)
            .pedidoNumero(DEFAULT_PEDIDO_NUMERO)
            .descripcion(DEFAULT_DESCRIPCION)
            .facturado(DEFAULT_FACTURADO)
            .entregado(DEFAULT_ENTREGADO)
            .totalBruto(DEFAULT_TOTAL_BRUTO)
            .totalIva(DEFAULT_TOTAL_IVA)
            .totalImpConsumo(DEFAULT_TOTAL_IMP_CONSUMO)
            .total(DEFAULT_TOTAL);
        return pedidoCabecera;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PedidoCabecera createUpdatedEntity(EntityManager em) {
        PedidoCabecera pedidoCabecera = new PedidoCabecera()
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .pedidoNumero(UPDATED_PEDIDO_NUMERO)
            .descripcion(UPDATED_DESCRIPCION)
            .facturado(UPDATED_FACTURADO)
            .entregado(UPDATED_ENTREGADO)
            .totalBruto(UPDATED_TOTAL_BRUTO)
            .totalIva(UPDATED_TOTAL_IVA)
            .totalImpConsumo(UPDATED_TOTAL_IMP_CONSUMO)
            .total(UPDATED_TOTAL);
        return pedidoCabecera;
    }

    @BeforeEach
    public void initTest() {
        pedidoCabecera = createEntity(em);
    }

    @Test
    @Transactional
    public void createPedidoCabecera() throws Exception {
        int databaseSizeBeforeCreate = pedidoCabeceraRepository.findAll().size();

        // Create the PedidoCabecera
        restPedidoCabeceraMockMvc.perform(post("/api/pedido-cabeceras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedidoCabecera)))
            .andExpect(status().isCreated());

        // Validate the PedidoCabecera in the database
        List<PedidoCabecera> pedidoCabeceraList = pedidoCabeceraRepository.findAll();
        assertThat(pedidoCabeceraList).hasSize(databaseSizeBeforeCreate + 1);
        PedidoCabecera testPedidoCabecera = pedidoCabeceraList.get(pedidoCabeceraList.size() - 1);
        assertThat(testPedidoCabecera.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testPedidoCabecera.getFechaEntrega()).isEqualTo(DEFAULT_FECHA_ENTREGA);
        assertThat(testPedidoCabecera.getPedidoNumero()).isEqualTo(DEFAULT_PEDIDO_NUMERO);
        assertThat(testPedidoCabecera.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPedidoCabecera.isFacturado()).isEqualTo(DEFAULT_FACTURADO);
        assertThat(testPedidoCabecera.isEntregado()).isEqualTo(DEFAULT_ENTREGADO);
        assertThat(testPedidoCabecera.getTotalBruto()).isEqualTo(DEFAULT_TOTAL_BRUTO);
        assertThat(testPedidoCabecera.getTotalIva()).isEqualTo(DEFAULT_TOTAL_IVA);
        assertThat(testPedidoCabecera.getTotalImpConsumo()).isEqualTo(DEFAULT_TOTAL_IMP_CONSUMO);
        assertThat(testPedidoCabecera.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createPedidoCabeceraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pedidoCabeceraRepository.findAll().size();

        // Create the PedidoCabecera with an existing ID
        pedidoCabecera.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoCabeceraMockMvc.perform(post("/api/pedido-cabeceras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedidoCabecera)))
            .andExpect(status().isBadRequest());

        // Validate the PedidoCabecera in the database
        List<PedidoCabecera> pedidoCabeceraList = pedidoCabeceraRepository.findAll();
        assertThat(pedidoCabeceraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPedidoCabeceras() throws Exception {
        // Initialize the database
        pedidoCabeceraRepository.saveAndFlush(pedidoCabecera);

        // Get all the pedidoCabeceraList
        restPedidoCabeceraMockMvc.perform(get("/api/pedido-cabeceras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedidoCabecera.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].pedidoNumero").value(hasItem(DEFAULT_PEDIDO_NUMERO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].facturado").value(hasItem(DEFAULT_FACTURADO.booleanValue())))
            .andExpect(jsonPath("$.[*].entregado").value(hasItem(DEFAULT_ENTREGADO.booleanValue())))
            .andExpect(jsonPath("$.[*].totalBruto").value(hasItem(DEFAULT_TOTAL_BRUTO.intValue())))
            .andExpect(jsonPath("$.[*].totalIva").value(hasItem(DEFAULT_TOTAL_IVA.intValue())))
            .andExpect(jsonPath("$.[*].totalImpConsumo").value(hasItem(DEFAULT_TOTAL_IMP_CONSUMO.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getPedidoCabecera() throws Exception {
        // Initialize the database
        pedidoCabeceraRepository.saveAndFlush(pedidoCabecera);

        // Get the pedidoCabecera
        restPedidoCabeceraMockMvc.perform(get("/api/pedido-cabeceras/{id}", pedidoCabecera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pedidoCabecera.getId().intValue()))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaEntrega").value(DEFAULT_FECHA_ENTREGA.toString()))
            .andExpect(jsonPath("$.pedidoNumero").value(DEFAULT_PEDIDO_NUMERO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.facturado").value(DEFAULT_FACTURADO.booleanValue()))
            .andExpect(jsonPath("$.entregado").value(DEFAULT_ENTREGADO.booleanValue()))
            .andExpect(jsonPath("$.totalBruto").value(DEFAULT_TOTAL_BRUTO.intValue()))
            .andExpect(jsonPath("$.totalIva").value(DEFAULT_TOTAL_IVA.intValue()))
            .andExpect(jsonPath("$.totalImpConsumo").value(DEFAULT_TOTAL_IMP_CONSUMO.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPedidoCabecera() throws Exception {
        // Get the pedidoCabecera
        restPedidoCabeceraMockMvc.perform(get("/api/pedido-cabeceras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePedidoCabecera() throws Exception {
        // Initialize the database
        pedidoCabeceraService.save(pedidoCabecera);

        int databaseSizeBeforeUpdate = pedidoCabeceraRepository.findAll().size();

        // Update the pedidoCabecera
        PedidoCabecera updatedPedidoCabecera = pedidoCabeceraRepository.findById(pedidoCabecera.getId()).get();
        // Disconnect from session so that the updates on updatedPedidoCabecera are not directly saved in db
        em.detach(updatedPedidoCabecera);
        updatedPedidoCabecera
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .pedidoNumero(UPDATED_PEDIDO_NUMERO)
            .descripcion(UPDATED_DESCRIPCION)
            .facturado(UPDATED_FACTURADO)
            .entregado(UPDATED_ENTREGADO)
            .totalBruto(UPDATED_TOTAL_BRUTO)
            .totalIva(UPDATED_TOTAL_IVA)
            .totalImpConsumo(UPDATED_TOTAL_IMP_CONSUMO)
            .total(UPDATED_TOTAL);

        restPedidoCabeceraMockMvc.perform(put("/api/pedido-cabeceras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPedidoCabecera)))
            .andExpect(status().isOk());

        // Validate the PedidoCabecera in the database
        List<PedidoCabecera> pedidoCabeceraList = pedidoCabeceraRepository.findAll();
        assertThat(pedidoCabeceraList).hasSize(databaseSizeBeforeUpdate);
        PedidoCabecera testPedidoCabecera = pedidoCabeceraList.get(pedidoCabeceraList.size() - 1);
        assertThat(testPedidoCabecera.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testPedidoCabecera.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testPedidoCabecera.getPedidoNumero()).isEqualTo(UPDATED_PEDIDO_NUMERO);
        assertThat(testPedidoCabecera.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPedidoCabecera.isFacturado()).isEqualTo(UPDATED_FACTURADO);
        assertThat(testPedidoCabecera.isEntregado()).isEqualTo(UPDATED_ENTREGADO);
        assertThat(testPedidoCabecera.getTotalBruto()).isEqualTo(UPDATED_TOTAL_BRUTO);
        assertThat(testPedidoCabecera.getTotalIva()).isEqualTo(UPDATED_TOTAL_IVA);
        assertThat(testPedidoCabecera.getTotalImpConsumo()).isEqualTo(UPDATED_TOTAL_IMP_CONSUMO);
        assertThat(testPedidoCabecera.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPedidoCabecera() throws Exception {
        int databaseSizeBeforeUpdate = pedidoCabeceraRepository.findAll().size();

        // Create the PedidoCabecera

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoCabeceraMockMvc.perform(put("/api/pedido-cabeceras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedidoCabecera)))
            .andExpect(status().isBadRequest());

        // Validate the PedidoCabecera in the database
        List<PedidoCabecera> pedidoCabeceraList = pedidoCabeceraRepository.findAll();
        assertThat(pedidoCabeceraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePedidoCabecera() throws Exception {
        // Initialize the database
        pedidoCabeceraService.save(pedidoCabecera);

        int databaseSizeBeforeDelete = pedidoCabeceraRepository.findAll().size();

        // Delete the pedidoCabecera
        restPedidoCabeceraMockMvc.perform(delete("/api/pedido-cabeceras/{id}", pedidoCabecera.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PedidoCabecera> pedidoCabeceraList = pedidoCabeceraRepository.findAll();
        assertThat(pedidoCabeceraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
