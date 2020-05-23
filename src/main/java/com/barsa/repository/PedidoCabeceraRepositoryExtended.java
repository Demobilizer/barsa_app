package com.barsa.repository;

import com.barsa.domain.Cliente;
import com.barsa.domain.PedidoCabecera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoCabeceraRepositoryExtended extends PedidoCabeceraRepository {

    @Query("SELECT p FROM PedidoCabecera p WHERE p.cliente = ?1 AND (p.fechaCreacion >= ?2 and p.fechaCreacion <= ?3)")
    List<PedidoCabecera> findByClienteAndFechaCreacion(Cliente cliente, Instant fechaCreacionStart, Instant fechaCreacionEnd);

    Optional<PedidoCabecera> findByPedidoNumero(Integer pedidoNo);

    List<PedidoCabecera> findByCliente(Cliente cliente);
}
