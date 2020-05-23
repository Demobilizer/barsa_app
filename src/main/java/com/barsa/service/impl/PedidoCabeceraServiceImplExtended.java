package com.barsa.service.impl;

import com.barsa.domain.Cliente;
import com.barsa.domain.PedidoCabecera;
import com.barsa.repository.ClienteRepositoryExtended;
import com.barsa.repository.PedidoCabeceraRepositoryExtended;
import com.barsa.service.PedidoCabeceraServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoCabeceraServiceImplExtended implements PedidoCabeceraServiceExtended {

    private final Logger log = LoggerFactory.getLogger(PedidoCabeceraServiceImpl.class);

    private final PedidoCabeceraRepositoryExtended pedidoCabeceraRepositoryExtended;

    private final ClienteRepositoryExtended clienteRepositoryExtended;

    public PedidoCabeceraServiceImplExtended(PedidoCabeceraRepositoryExtended pedidoCabeceraRepositoryExtended, ClienteRepositoryExtended clienteRepositoryExtended) {
        this.pedidoCabeceraRepositoryExtended = pedidoCabeceraRepositoryExtended;
        this.clienteRepositoryExtended = clienteRepositoryExtended;
    }

    @Override
    public PedidoCabecera save(PedidoCabecera pedidoCabecera) {
        return null;
    }

    @Override
    public List<PedidoCabecera> findAll() {
        return null;
    }

    @Override
    public Optional<PedidoCabecera> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<PedidoCabecera> findAllByClient(Integer noIdentification) {
        Cliente cliente = clienteRepositoryExtended.findByNoIdentificacion(noIdentification);
        if (cliente.equals(null) || cliente == null) {
            log.debug("Client is not present with the noIdentification: ", noIdentification);
            throw new NullPointerException("no Pedido Cabecera found for given noIdentification : "+noIdentification);
        } else {
            return pedidoCabeceraRepositoryExtended.findByCliente(cliente);
        }

    }

    @Override
    public List<PedidoCabecera> findAllByClientAndFechaCreacion(Integer noIdentification, LocalDate fechaCreacion) {
        Cliente cliente = clienteRepositoryExtended.findByNoIdentificacion(noIdentification);
        if (cliente.equals(null) || cliente == null) {
            log.debug("Client is not present with the noIdentification: ", noIdentification);
            throw new NullPointerException("no Pedido Cabecera found for given noIdentification : "+noIdentification);
        } else {
            LocalDateTime localDateTimeFrom = fechaCreacion.atStartOfDay();
            LocalDateTime localDateTimeTo = fechaCreacion.plusDays(1).atStartOfDay();
            Instant fechaCreacionStart = localDateTimeFrom.toInstant(ZoneOffset.UTC);
            Instant fechaCreacionEnd = localDateTimeTo.toInstant(ZoneOffset.UTC);

            return pedidoCabeceraRepositoryExtended.findByClienteAndFechaCreacion(cliente, fechaCreacionStart, fechaCreacionEnd);
        }
    }
}
