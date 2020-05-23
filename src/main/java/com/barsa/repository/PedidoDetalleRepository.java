package com.barsa.repository;

import com.barsa.domain.PedidoDetalle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PedidoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {
}
