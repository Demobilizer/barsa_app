package com.barsa.repository;

import com.barsa.domain.ListaPrecios;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ListaPrecios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListaPreciosRepository extends JpaRepository<ListaPrecios, Long> {
}
