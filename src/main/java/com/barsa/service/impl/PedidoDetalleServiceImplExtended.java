package com.barsa.service.impl;

import com.barsa.domain.PedidoCabecera;
import com.barsa.domain.PedidoDetalle;
import com.barsa.repository.PedidoCabeceraRepositoryExtended;
import com.barsa.repository.PedidoDetalleRepositoryExtended;
import com.barsa.service.PedidoDetalleServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoDetalleServiceImplExtended implements PedidoDetalleServiceExtended {

    private final Logger log = LoggerFactory.getLogger(PedidoDetalleServiceImpl.class);

    private final PedidoCabeceraRepositoryExtended pedidoCabeceraRepositoryExtended;

    private final PedidoDetalleRepositoryExtended pedidoDetalleRepositoryExtended;

    public PedidoDetalleServiceImplExtended(PedidoCabeceraRepositoryExtended pedidoCabeceraRepositoryExtended, PedidoDetalleRepositoryExtended pedidoDetalleRepositoryExtended) {
        this.pedidoCabeceraRepositoryExtended = pedidoCabeceraRepositoryExtended;
        this.pedidoDetalleRepositoryExtended = pedidoDetalleRepositoryExtended;
    }

    @Override
    public PedidoDetalle save(PedidoDetalle pedidoDetalle) {
        return null;
    }

    @Override
    public List<PedidoDetalle> findAll() {
        return null;
    }

    @Override
    public Optional<PedidoDetalle> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<PedidoDetalle> findAllByPedidoNumero(Integer pedidoNo) {
        PedidoCabecera pedidoCabecera = pedidoCabeceraRepositoryExtended.findByPedidoNumero(pedidoNo).orElse(null);
        if (pedidoCabecera == null || pedidoCabecera.equals(null)) {
            log.debug("pedidoCabecera is null for given pedidoNo : {}", pedidoNo);
            throw new NullPointerException("no Pedido Cabecera found for given pedido Numero : "+pedidoNo);
        } else {
            return pedidoDetalleRepositoryExtended.findAllByPedidoNumero(Optional.of(pedidoCabecera));
        }
    }
}
