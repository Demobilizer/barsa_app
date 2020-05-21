package com.barsa.service.impl;

import com.barsa.domain.Cliente;
import com.barsa.repository.ClienteRepository;
import com.barsa.service.PedidoCabeceraService;
import com.barsa.domain.PedidoCabecera;
import com.barsa.repository.PedidoCabeceraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PedidoCabecera}.
 */
@Service
@Transactional
public class PedidoCabeceraServiceImpl implements PedidoCabeceraService {

    private final Logger log = LoggerFactory.getLogger(PedidoCabeceraServiceImpl.class);

    private final PedidoCabeceraRepository pedidoCabeceraRepository;

    private final ClienteRepository clienteRepository;

    public PedidoCabeceraServiceImpl(PedidoCabeceraRepository pedidoCabeceraRepository, ClienteRepository clienteRepository) {
        this.pedidoCabeceraRepository = pedidoCabeceraRepository;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Save a pedidoCabecera.
     *
     * @param pedidoCabecera the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PedidoCabecera save(PedidoCabecera pedidoCabecera) {
        log.debug("Request to save PedidoCabecera : {}", pedidoCabecera);
        return pedidoCabeceraRepository.save(pedidoCabecera);
    }

    /**
     * Get all the pedidoCabeceras.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PedidoCabecera> findAll() {
        log.debug("Request to get all PedidoCabeceras");
        return pedidoCabeceraRepository.findAll();
    }

    /**
     * Get one pedidoCabecera by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoCabecera> findOne(Long id) {
        log.debug("Request to get PedidoCabecera : {}", id);
        return pedidoCabeceraRepository.findById(id);
    }

    /**
     * Delete the pedidoCabecera by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PedidoCabecera : {}", id);
        pedidoCabeceraRepository.deleteById(id);
    }

    @Override
    public List<PedidoCabecera> findAllByClient(Integer noIdentification) {
        Cliente cliente = clienteRepository.findByNoIdentificacion(noIdentification);
        if (cliente.equals(null) || cliente == null) {
            log.debug("Client is not present with the noIdentification: ", noIdentification);
            throw new NullPointerException();
        } else {
            return pedidoCabeceraRepository.findByClienteNo(cliente);
        }

    }

    @Override
    public List<PedidoCabecera> findAllByClientAndFechaCreacion(Integer noIdentification, LocalDate fechaCreacion) {
        Cliente cliente = clienteRepository.findByNoIdentificacion(noIdentification);
        if (cliente.equals(null) || cliente == null) {
            log.debug("Client is not present with the noIdentification: ", noIdentification);
            throw new NullPointerException();
        } else {
            LocalDateTime localDateTimeFrom = fechaCreacion.atStartOfDay();
            LocalDateTime localDateTimeTo = fechaCreacion.plusDays(1).atStartOfDay();
            Instant fechaCreacionStart = localDateTimeFrom.toInstant(ZoneOffset.UTC);
            Instant fechaCreacionEnd = localDateTimeTo.toInstant(ZoneOffset.UTC);

            return pedidoCabeceraRepository.findByClienteNoAndFechaCreacion(cliente, fechaCreacionStart, fechaCreacionEnd);
        }
    }
}
