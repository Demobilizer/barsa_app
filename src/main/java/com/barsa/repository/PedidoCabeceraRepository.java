package com.barsa.repository;

import com.barsa.domain.Cliente;
import com.barsa.domain.PedidoCabecera;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the PedidoCabecera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoCabeceraRepository extends JpaRepository<PedidoCabecera, Long> {
    List<PedidoCabecera> findByClienteNo(Cliente cliente);

    @Query("SELECT p FROM PedidoCabecera p WHERE p.clienteNo = ?1 AND (p.fechaCreacion >= ?2 and p.fechaCreacion <= ?3)")
    List<PedidoCabecera> findByClienteNoAndFechaCreacion(Cliente cliente, Instant fechaCreacionStart, Instant fechaCreacionEnd);
}
