package com.barsa.web.rest;

import com.barsa.domain.PedidoDetalle;
import com.barsa.service.PedidoDetalleService;
import com.barsa.service.PedidoDetalleServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/extended")
public class PedidoDetalleResourceExtended {

    private final Logger log = LoggerFactory.getLogger(PedidoDetalleResource.class);

    private final PedidoDetalleServiceExtended pedidoDetalleServiceExtended;

    public PedidoDetalleResourceExtended(PedidoDetalleServiceExtended pedidoDetalleServiceExtended) {
        this.pedidoDetalleServiceExtended = pedidoDetalleServiceExtended;
    }

    @GetMapping("/pedido-detalles/{pedidoNumero}")
    public List<PedidoDetalle> getPedidoDetailsByPedidoNo(@PathVariable Integer pedidoNumero) {
        log.debug("REST request to get PedidoDetalle by pedidoNo : {}", pedidoNumero);
        if (pedidoDetalleServiceExtended.findAllByPedidoNumero(pedidoNumero).isEmpty()) {
            throw new NullPointerException("no Pedido Details found for given pedido Numero : "+pedidoNumero);
        }
        return pedidoDetalleServiceExtended.findAllByPedidoNumero(pedidoNumero);
    }

}
