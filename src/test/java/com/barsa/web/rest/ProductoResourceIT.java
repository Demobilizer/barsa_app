package com.barsa.web.rest;

import com.barsa.BarsaAppApp;
import com.barsa.domain.Producto;
import com.barsa.repository.ProductoRepository;
import com.barsa.service.ProductoService;
import com.barsa.service.dto.ProductoCriteria;
import com.barsa.service.ProductoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductoResource} REST controller.
 */
@SpringBootTest(classes = BarsaAppApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ProductoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_UNIDAD = "AAAAAAAAAA";
    private static final String UPDATED_UNIDAD = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;
    private static final Integer SMALLER_CANTIDAD = 1 - 1;

    private static final BigDecimal DEFAULT_PRECIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRECIO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_IVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_IVA = new BigDecimal(2);
    private static final BigDecimal SMALLER_IVA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ICOVALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_ICOVALOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_ICOVALOR = new BigDecimal(1 - 1);

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoQueryService productoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoMockMvc;

    private Producto producto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .codigo(DEFAULT_CODIGO)
            .descripcion(DEFAULT_DESCRIPCION)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .unidad(DEFAULT_UNIDAD)
            .cantidad(DEFAULT_CANTIDAD)
            .precio(DEFAULT_PRECIO)
            .iva(DEFAULT_IVA)
            .icovalor(DEFAULT_ICOVALOR);
        return producto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createUpdatedEntity(EntityManager em) {
        Producto producto = new Producto()
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .unidad(UPDATED_UNIDAD)
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO)
            .iva(UPDATED_IVA)
            .icovalor(UPDATED_ICOVALOR);
        return producto;
    }

    @BeforeEach
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProducto.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testProducto.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testProducto.getUnidad()).isEqualTo(DEFAULT_UNIDAD);
        assertThat(testProducto.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProducto.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testProducto.getIva()).isEqualTo(DEFAULT_IVA);
        assertThat(testProducto.getIcovalor()).isEqualTo(DEFAULT_ICOVALOR);
    }

    @Test
    @Transactional
    public void createProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto with an existing ID
        producto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())))
            .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA.intValue())))
            .andExpect(jsonPath("$.[*].icovalor").value(hasItem(DEFAULT_ICOVALOR.intValue())));
    }
    
    @Test
    @Transactional
    public void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.unidad").value(DEFAULT_UNIDAD))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA.intValue()))
            .andExpect(jsonPath("$.icovalor").value(DEFAULT_ICOVALOR.intValue()));
    }


    @Test
    @Transactional
    public void getProductosByIdFiltering() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        Long id = producto.getId();

        defaultProductoShouldBeFound("id.equals=" + id);
        defaultProductoShouldNotBeFound("id.notEquals=" + id);

        defaultProductoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductoShouldNotBeFound("id.greaterThan=" + id);

        defaultProductoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductosByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigo equals to DEFAULT_CODIGO
        defaultProductoShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the productoList where codigo equals to UPDATED_CODIGO
        defaultProductoShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllProductosByCodigoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigo not equals to DEFAULT_CODIGO
        defaultProductoShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

        // Get all the productoList where codigo not equals to UPDATED_CODIGO
        defaultProductoShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllProductosByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultProductoShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the productoList where codigo equals to UPDATED_CODIGO
        defaultProductoShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllProductosByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigo is not null
        defaultProductoShouldBeFound("codigo.specified=true");

        // Get all the productoList where codigo is null
        defaultProductoShouldNotBeFound("codigo.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductosByCodigoContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigo contains DEFAULT_CODIGO
        defaultProductoShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the productoList where codigo contains UPDATED_CODIGO
        defaultProductoShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllProductosByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigo does not contain DEFAULT_CODIGO
        defaultProductoShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the productoList where codigo does not contain UPDATED_CODIGO
        defaultProductoShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }


    @Test
    @Transactional
    public void getAllProductosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion equals to DEFAULT_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the productoList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the productoList where descripcion not equals to UPDATED_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the productoList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion is not null
        defaultProductoShouldBeFound("descripcion.specified=true");

        // Get all the productoList where descripcion is null
        defaultProductoShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductosByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion contains DEFAULT_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the productoList where descripcion contains UPDATED_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the productoList where descripcion does not contain UPDATED_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }


    @Test
    @Transactional
    public void getAllProductosByUnidadIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where unidad equals to DEFAULT_UNIDAD
        defaultProductoShouldBeFound("unidad.equals=" + DEFAULT_UNIDAD);

        // Get all the productoList where unidad equals to UPDATED_UNIDAD
        defaultProductoShouldNotBeFound("unidad.equals=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where unidad not equals to DEFAULT_UNIDAD
        defaultProductoShouldNotBeFound("unidad.notEquals=" + DEFAULT_UNIDAD);

        // Get all the productoList where unidad not equals to UPDATED_UNIDAD
        defaultProductoShouldBeFound("unidad.notEquals=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where unidad in DEFAULT_UNIDAD or UPDATED_UNIDAD
        defaultProductoShouldBeFound("unidad.in=" + DEFAULT_UNIDAD + "," + UPDATED_UNIDAD);

        // Get all the productoList where unidad equals to UPDATED_UNIDAD
        defaultProductoShouldNotBeFound("unidad.in=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where unidad is not null
        defaultProductoShouldBeFound("unidad.specified=true");

        // Get all the productoList where unidad is null
        defaultProductoShouldNotBeFound("unidad.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductosByUnidadContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where unidad contains DEFAULT_UNIDAD
        defaultProductoShouldBeFound("unidad.contains=" + DEFAULT_UNIDAD);

        // Get all the productoList where unidad contains UPDATED_UNIDAD
        defaultProductoShouldNotBeFound("unidad.contains=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadNotContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where unidad does not contain DEFAULT_UNIDAD
        defaultProductoShouldNotBeFound("unidad.doesNotContain=" + DEFAULT_UNIDAD);

        // Get all the productoList where unidad does not contain UPDATED_UNIDAD
        defaultProductoShouldBeFound("unidad.doesNotContain=" + UPDATED_UNIDAD);
    }


    @Test
    @Transactional
    public void getAllProductosByCantidadIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cantidad equals to DEFAULT_CANTIDAD
        defaultProductoShouldBeFound("cantidad.equals=" + DEFAULT_CANTIDAD);

        // Get all the productoList where cantidad equals to UPDATED_CANTIDAD
        defaultProductoShouldNotBeFound("cantidad.equals=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByCantidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cantidad not equals to DEFAULT_CANTIDAD
        defaultProductoShouldNotBeFound("cantidad.notEquals=" + DEFAULT_CANTIDAD);

        // Get all the productoList where cantidad not equals to UPDATED_CANTIDAD
        defaultProductoShouldBeFound("cantidad.notEquals=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByCantidadIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cantidad in DEFAULT_CANTIDAD or UPDATED_CANTIDAD
        defaultProductoShouldBeFound("cantidad.in=" + DEFAULT_CANTIDAD + "," + UPDATED_CANTIDAD);

        // Get all the productoList where cantidad equals to UPDATED_CANTIDAD
        defaultProductoShouldNotBeFound("cantidad.in=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByCantidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cantidad is not null
        defaultProductoShouldBeFound("cantidad.specified=true");

        // Get all the productoList where cantidad is null
        defaultProductoShouldNotBeFound("cantidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByCantidadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cantidad is greater than or equal to DEFAULT_CANTIDAD
        defaultProductoShouldBeFound("cantidad.greaterThanOrEqual=" + DEFAULT_CANTIDAD);

        // Get all the productoList where cantidad is greater than or equal to UPDATED_CANTIDAD
        defaultProductoShouldNotBeFound("cantidad.greaterThanOrEqual=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByCantidadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cantidad is less than or equal to DEFAULT_CANTIDAD
        defaultProductoShouldBeFound("cantidad.lessThanOrEqual=" + DEFAULT_CANTIDAD);

        // Get all the productoList where cantidad is less than or equal to SMALLER_CANTIDAD
        defaultProductoShouldNotBeFound("cantidad.lessThanOrEqual=" + SMALLER_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByCantidadIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cantidad is less than DEFAULT_CANTIDAD
        defaultProductoShouldNotBeFound("cantidad.lessThan=" + DEFAULT_CANTIDAD);

        // Get all the productoList where cantidad is less than UPDATED_CANTIDAD
        defaultProductoShouldBeFound("cantidad.lessThan=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByCantidadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cantidad is greater than DEFAULT_CANTIDAD
        defaultProductoShouldNotBeFound("cantidad.greaterThan=" + DEFAULT_CANTIDAD);

        // Get all the productoList where cantidad is greater than SMALLER_CANTIDAD
        defaultProductoShouldBeFound("cantidad.greaterThan=" + SMALLER_CANTIDAD);
    }


    @Test
    @Transactional
    public void getAllProductosByPrecioIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio equals to DEFAULT_PRECIO
        defaultProductoShouldBeFound("precio.equals=" + DEFAULT_PRECIO);

        // Get all the productoList where precio equals to UPDATED_PRECIO
        defaultProductoShouldNotBeFound("precio.equals=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio not equals to DEFAULT_PRECIO
        defaultProductoShouldNotBeFound("precio.notEquals=" + DEFAULT_PRECIO);

        // Get all the productoList where precio not equals to UPDATED_PRECIO
        defaultProductoShouldBeFound("precio.notEquals=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio in DEFAULT_PRECIO or UPDATED_PRECIO
        defaultProductoShouldBeFound("precio.in=" + DEFAULT_PRECIO + "," + UPDATED_PRECIO);

        // Get all the productoList where precio equals to UPDATED_PRECIO
        defaultProductoShouldNotBeFound("precio.in=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is not null
        defaultProductoShouldBeFound("precio.specified=true");

        // Get all the productoList where precio is null
        defaultProductoShouldNotBeFound("precio.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is greater than or equal to DEFAULT_PRECIO
        defaultProductoShouldBeFound("precio.greaterThanOrEqual=" + DEFAULT_PRECIO);

        // Get all the productoList where precio is greater than or equal to UPDATED_PRECIO
        defaultProductoShouldNotBeFound("precio.greaterThanOrEqual=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is less than or equal to DEFAULT_PRECIO
        defaultProductoShouldBeFound("precio.lessThanOrEqual=" + DEFAULT_PRECIO);

        // Get all the productoList where precio is less than or equal to SMALLER_PRECIO
        defaultProductoShouldNotBeFound("precio.lessThanOrEqual=" + SMALLER_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is less than DEFAULT_PRECIO
        defaultProductoShouldNotBeFound("precio.lessThan=" + DEFAULT_PRECIO);

        // Get all the productoList where precio is less than UPDATED_PRECIO
        defaultProductoShouldBeFound("precio.lessThan=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is greater than DEFAULT_PRECIO
        defaultProductoShouldNotBeFound("precio.greaterThan=" + DEFAULT_PRECIO);

        // Get all the productoList where precio is greater than SMALLER_PRECIO
        defaultProductoShouldBeFound("precio.greaterThan=" + SMALLER_PRECIO);
    }


    @Test
    @Transactional
    public void getAllProductosByIvaIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where iva equals to DEFAULT_IVA
        defaultProductoShouldBeFound("iva.equals=" + DEFAULT_IVA);

        // Get all the productoList where iva equals to UPDATED_IVA
        defaultProductoShouldNotBeFound("iva.equals=" + UPDATED_IVA);
    }

    @Test
    @Transactional
    public void getAllProductosByIvaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where iva not equals to DEFAULT_IVA
        defaultProductoShouldNotBeFound("iva.notEquals=" + DEFAULT_IVA);

        // Get all the productoList where iva not equals to UPDATED_IVA
        defaultProductoShouldBeFound("iva.notEquals=" + UPDATED_IVA);
    }

    @Test
    @Transactional
    public void getAllProductosByIvaIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where iva in DEFAULT_IVA or UPDATED_IVA
        defaultProductoShouldBeFound("iva.in=" + DEFAULT_IVA + "," + UPDATED_IVA);

        // Get all the productoList where iva equals to UPDATED_IVA
        defaultProductoShouldNotBeFound("iva.in=" + UPDATED_IVA);
    }

    @Test
    @Transactional
    public void getAllProductosByIvaIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where iva is not null
        defaultProductoShouldBeFound("iva.specified=true");

        // Get all the productoList where iva is null
        defaultProductoShouldNotBeFound("iva.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByIvaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where iva is greater than or equal to DEFAULT_IVA
        defaultProductoShouldBeFound("iva.greaterThanOrEqual=" + DEFAULT_IVA);

        // Get all the productoList where iva is greater than or equal to UPDATED_IVA
        defaultProductoShouldNotBeFound("iva.greaterThanOrEqual=" + UPDATED_IVA);
    }

    @Test
    @Transactional
    public void getAllProductosByIvaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where iva is less than or equal to DEFAULT_IVA
        defaultProductoShouldBeFound("iva.lessThanOrEqual=" + DEFAULT_IVA);

        // Get all the productoList where iva is less than or equal to SMALLER_IVA
        defaultProductoShouldNotBeFound("iva.lessThanOrEqual=" + SMALLER_IVA);
    }

    @Test
    @Transactional
    public void getAllProductosByIvaIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where iva is less than DEFAULT_IVA
        defaultProductoShouldNotBeFound("iva.lessThan=" + DEFAULT_IVA);

        // Get all the productoList where iva is less than UPDATED_IVA
        defaultProductoShouldBeFound("iva.lessThan=" + UPDATED_IVA);
    }

    @Test
    @Transactional
    public void getAllProductosByIvaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where iva is greater than DEFAULT_IVA
        defaultProductoShouldNotBeFound("iva.greaterThan=" + DEFAULT_IVA);

        // Get all the productoList where iva is greater than SMALLER_IVA
        defaultProductoShouldBeFound("iva.greaterThan=" + SMALLER_IVA);
    }


    @Test
    @Transactional
    public void getAllProductosByIcovalorIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where icovalor equals to DEFAULT_ICOVALOR
        defaultProductoShouldBeFound("icovalor.equals=" + DEFAULT_ICOVALOR);

        // Get all the productoList where icovalor equals to UPDATED_ICOVALOR
        defaultProductoShouldNotBeFound("icovalor.equals=" + UPDATED_ICOVALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByIcovalorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where icovalor not equals to DEFAULT_ICOVALOR
        defaultProductoShouldNotBeFound("icovalor.notEquals=" + DEFAULT_ICOVALOR);

        // Get all the productoList where icovalor not equals to UPDATED_ICOVALOR
        defaultProductoShouldBeFound("icovalor.notEquals=" + UPDATED_ICOVALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByIcovalorIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where icovalor in DEFAULT_ICOVALOR or UPDATED_ICOVALOR
        defaultProductoShouldBeFound("icovalor.in=" + DEFAULT_ICOVALOR + "," + UPDATED_ICOVALOR);

        // Get all the productoList where icovalor equals to UPDATED_ICOVALOR
        defaultProductoShouldNotBeFound("icovalor.in=" + UPDATED_ICOVALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByIcovalorIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where icovalor is not null
        defaultProductoShouldBeFound("icovalor.specified=true");

        // Get all the productoList where icovalor is null
        defaultProductoShouldNotBeFound("icovalor.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByIcovalorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where icovalor is greater than or equal to DEFAULT_ICOVALOR
        defaultProductoShouldBeFound("icovalor.greaterThanOrEqual=" + DEFAULT_ICOVALOR);

        // Get all the productoList where icovalor is greater than or equal to UPDATED_ICOVALOR
        defaultProductoShouldNotBeFound("icovalor.greaterThanOrEqual=" + UPDATED_ICOVALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByIcovalorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where icovalor is less than or equal to DEFAULT_ICOVALOR
        defaultProductoShouldBeFound("icovalor.lessThanOrEqual=" + DEFAULT_ICOVALOR);

        // Get all the productoList where icovalor is less than or equal to SMALLER_ICOVALOR
        defaultProductoShouldNotBeFound("icovalor.lessThanOrEqual=" + SMALLER_ICOVALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByIcovalorIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where icovalor is less than DEFAULT_ICOVALOR
        defaultProductoShouldNotBeFound("icovalor.lessThan=" + DEFAULT_ICOVALOR);

        // Get all the productoList where icovalor is less than UPDATED_ICOVALOR
        defaultProductoShouldBeFound("icovalor.lessThan=" + UPDATED_ICOVALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByIcovalorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where icovalor is greater than DEFAULT_ICOVALOR
        defaultProductoShouldNotBeFound("icovalor.greaterThan=" + DEFAULT_ICOVALOR);

        // Get all the productoList where icovalor is greater than SMALLER_ICOVALOR
        defaultProductoShouldBeFound("icovalor.greaterThan=" + SMALLER_ICOVALOR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductoShouldBeFound(String filter) throws Exception {
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())))
            .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA.intValue())))
            .andExpect(jsonPath("$.[*].icovalor").value(hasItem(DEFAULT_ICOVALOR.intValue())));

        // Check, that the count call also returns 1
        restProductoMockMvc.perform(get("/api/productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductoShouldNotBeFound(String filter) throws Exception {
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductoMockMvc.perform(get("/api/productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducto() throws Exception {
        // Initialize the database
        productoService.save(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .unidad(UPDATED_UNIDAD)
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO)
            .iva(UPDATED_IVA)
            .icovalor(UPDATED_ICOVALOR);

        restProductoMockMvc.perform(put("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProducto)))
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testProducto.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testProducto.getUnidad()).isEqualTo(UPDATED_UNIDAD);
        assertThat(testProducto.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProducto.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testProducto.getIva()).isEqualTo(UPDATED_IVA);
        assertThat(testProducto.getIcovalor()).isEqualTo(UPDATED_ICOVALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Create the Producto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc.perform(put("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducto() throws Exception {
        // Initialize the database
        productoService.save(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Delete the producto
        restProductoMockMvc.perform(delete("/api/productos/{id}", producto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
