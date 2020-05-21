package com.barsa.service.impl;

import com.barsa.domain.PedidoCabecera;
import com.barsa.repository.PedidoCabeceraRepository;
import com.barsa.service.PedidoDetalleService;
import com.barsa.domain.PedidoDetalle;
import com.barsa.repository.PedidoDetalleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PedidoDetalle}.
 */
@Service
@Transactional
public class PedidoDetalleServiceImpl implements PedidoDetalleService {

    private final Logger log = LoggerFactory.getLogger(PedidoDetalleServiceImpl.class);

    private final PedidoDetalleRepository pedidoDetalleRepository;

    private final PedidoCabeceraRepository pedidoCabeceraRepository;

    public PedidoDetalleServiceImpl(PedidoDetalleRepository pedidoDetalleRepository, PedidoCabeceraRepository pedidoCabeceraRepository) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.pedidoCabeceraRepository = pedidoCabeceraRepository;
    }

    /**
     * Save a pedidoDetalle.
     *
     * @param pedidoDetalle the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PedidoDetalle save(PedidoDetalle pedidoDetalle) {
        log.debug("Request to save PedidoDetalle : {}", pedidoDetalle);
        return pedidoDetalleRepository.save(pedidoDetalle);
    }

    /**
     * Get all the pedidoDetalles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PedidoDetalle> findAll() {
        log.debug("Request to get all PedidoDetalles");
        return pedidoDetalleRepository.findAll();
    }

    /**
     * Get one pedidoDetalle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoDetalle> findOne(Long id) {
        log.debug("Request to get PedidoDetalle : {}", id);
        return pedidoDetalleRepository.findById(id);
    }

    /**
     * Delete the pedidoDetalle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PedidoDetalle : {}", id);
        pedidoDetalleRepository.deleteById(id);
    }

    @Override
    public List<PedidoDetalle> findAllByPedidoNumero(Integer pedidoNo) {
        PedidoCabecera pedidoCabecera = pedidoCabeceraRepository.findById(pedidoNo.longValue()).orElse(null);
        if (pedidoCabecera == null || pedidoCabecera.equals(null)) {
            log.debug("pedidoCabecera is null for given pedidoNo : {}", pedidoNo);
            throw new NullPointerException();
        } else {
            return pedidoDetalleRepository.findAllByPedidoNumero(Optional.of(pedidoCabecera));
        }
    }
}
