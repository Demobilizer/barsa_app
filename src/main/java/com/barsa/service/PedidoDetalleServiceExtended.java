package com.barsa.service;

import com.barsa.domain.PedidoDetalle;

import java.util.List;

public interface PedidoDetalleServiceExtended extends PedidoDetalleService {

    List<PedidoDetalle> findAllByPedidoNumero(Integer pedidoNo);
}
