package com.barsa.web.rest;

import com.barsa.BarsaAppApp;
import com.barsa.domain.ListaPrecios;
import com.barsa.repository.ListaPreciosRepository;
import com.barsa.service.ListaPreciosService;

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
 * Integration tests for the {@link ListaPreciosResource} REST controller.
 */
@SpringBootTest(classes = BarsaAppApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ListaPreciosResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PORCENTAJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PORCENTAJE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private ListaPreciosRepository listaPreciosRepository;

    @Autowired
    private ListaPreciosService listaPreciosService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListaPreciosMockMvc;

    private ListaPrecios listaPrecios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListaPrecios createEntity(EntityManager em) {
        ListaPrecios listaPrecios = new ListaPrecios()
            .descripcion(DEFAULT_DESCRIPCION)
            .porcentaje(DEFAULT_PORCENTAJE)
            .valor(DEFAULT_VALOR);
        return listaPrecios;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListaPrecios createUpdatedEntity(EntityManager em) {
        ListaPrecios listaPrecios = new ListaPrecios()
            .descripcion(UPDATED_DESCRIPCION)
            .porcentaje(UPDATED_PORCENTAJE)
            .valor(UPDATED_VALOR);
        return listaPrecios;
    }

    @BeforeEach
    public void initTest() {
        listaPrecios = createEntity(em);
    }

    @Test
    @Transactional
    public void createListaPrecios() throws Exception {
        int databaseSizeBeforeCreate = listaPreciosRepository.findAll().size();

        // Create the ListaPrecios
        restListaPreciosMockMvc.perform(post("/api/lista-precios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listaPrecios)))
            .andExpect(status().isCreated());

        // Validate the ListaPrecios in the database
        List<ListaPrecios> listaPreciosList = listaPreciosRepository.findAll();
        assertThat(listaPreciosList).hasSize(databaseSizeBeforeCreate + 1);
        ListaPrecios testListaPrecios = listaPreciosList.get(listaPreciosList.size() - 1);
        assertThat(testListaPrecios.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testListaPrecios.getPorcentaje()).isEqualTo(DEFAULT_PORCENTAJE);
        assertThat(testListaPrecios.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createListaPreciosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listaPreciosRepository.findAll().size();

        // Create the ListaPrecios with an existing ID
        listaPrecios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListaPreciosMockMvc.perform(post("/api/lista-precios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listaPrecios)))
            .andExpect(status().isBadRequest());

        // Validate the ListaPrecios in the database
        List<ListaPrecios> listaPreciosList = listaPreciosRepository.findAll();
        assertThat(listaPreciosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllListaPrecios() throws Exception {
        // Initialize the database
        listaPreciosRepository.saveAndFlush(listaPrecios);

        // Get all the listaPreciosList
        restListaPreciosMockMvc.perform(get("/api/lista-precios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listaPrecios.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].porcentaje").value(hasItem(DEFAULT_PORCENTAJE.intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }
    
    @Test
    @Transactional
    public void getListaPrecios() throws Exception {
        // Initialize the database
        listaPreciosRepository.saveAndFlush(listaPrecios);

        // Get the listaPrecios
        restListaPreciosMockMvc.perform(get("/api/lista-precios/{id}", listaPrecios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listaPrecios.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.porcentaje").value(DEFAULT_PORCENTAJE.intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingListaPrecios() throws Exception {
        // Get the listaPrecios
        restListaPreciosMockMvc.perform(get("/api/lista-precios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListaPrecios() throws Exception {
        // Initialize the database
        listaPreciosService.save(listaPrecios);

        int databaseSizeBeforeUpdate = listaPreciosRepository.findAll().size();

        // Update the listaPrecios
        ListaPrecios updatedListaPrecios = listaPreciosRepository.findById(listaPrecios.getId()).get();
        // Disconnect from session so that the updates on updatedListaPrecios are not directly saved in db
        em.detach(updatedListaPrecios);
        updatedListaPrecios
            .descripcion(UPDATED_DESCRIPCION)
            .porcentaje(UPDATED_PORCENTAJE)
            .valor(UPDATED_VALOR);

        restListaPreciosMockMvc.perform(put("/api/lista-precios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedListaPrecios)))
            .andExpect(status().isOk());

        // Validate the ListaPrecios in the database
        List<ListaPrecios> listaPreciosList = listaPreciosRepository.findAll();
        assertThat(listaPreciosList).hasSize(databaseSizeBeforeUpdate);
        ListaPrecios testListaPrecios = listaPreciosList.get(listaPreciosList.size() - 1);
        assertThat(testListaPrecios.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testListaPrecios.getPorcentaje()).isEqualTo(UPDATED_PORCENTAJE);
        assertThat(testListaPrecios.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingListaPrecios() throws Exception {
        int databaseSizeBeforeUpdate = listaPreciosRepository.findAll().size();

        // Create the ListaPrecios

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListaPreciosMockMvc.perform(put("/api/lista-precios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listaPrecios)))
            .andExpect(status().isBadRequest());

        // Validate the ListaPrecios in the database
        List<ListaPrecios> listaPreciosList = listaPreciosRepository.findAll();
        assertThat(listaPreciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListaPrecios() throws Exception {
        // Initialize the database
        listaPreciosService.save(listaPrecios);

        int databaseSizeBeforeDelete = listaPreciosRepository.findAll().size();

        // Delete the listaPrecios
        restListaPreciosMockMvc.perform(delete("/api/lista-precios/{id}", listaPrecios.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListaPrecios> listaPreciosList = listaPreciosRepository.findAll();
        assertThat(listaPreciosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
