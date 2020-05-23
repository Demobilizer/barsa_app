package com.barsa.repository;

import com.barsa.domain.PedidoCabecera;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PedidoCabecera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoCabeceraRepository extends JpaRepository<PedidoCabecera, Long> {
}
