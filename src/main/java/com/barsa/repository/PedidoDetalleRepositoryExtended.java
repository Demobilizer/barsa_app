package com.barsa.repository;

import com.barsa.domain.PedidoCabecera;
import com.barsa.domain.PedidoDetalle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoDetalleRepositoryExtended extends PedidoDetalleRepository {

    List<PedidoDetalle> findAllByPedidoNumero(Optional<PedidoCabecera> pedidoNo);
}
