package com.barsa.service;

import com.barsa.domain.PedidoCabecera;

import java.time.LocalDate;
import java.util.List;

public interface PedidoCabeceraServiceExtended extends PedidoCabeceraService {

    List<PedidoCabecera> findAllByClient(Integer noIdentification);

    List<PedidoCabecera> findAllByClientAndFechaCreacion(Integer noIdentification, LocalDate fechaCreacion);
}
