package com.barsa.web.rest;


import com.barsa.domain.PedidoCabecera;
import com.barsa.service.PedidoCabeceraServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/extended")
public class PedidoCabeceraResourceExtended {

    private final Logger log = LoggerFactory.getLogger(PedidoCabeceraResource.class);

    private final PedidoCabeceraServiceExtended pedidoCabeceraServiceExtended;

    public PedidoCabeceraResourceExtended(PedidoCabeceraServiceExtended pedidoCabeceraServiceExtended) {
        this.pedidoCabeceraServiceExtended = pedidoCabeceraServiceExtended;
    }

    @GetMapping("/pedido-cabeceras/{noIdentification}")
    public List<PedidoCabecera> getPedidoCabeceraByClient(@PathVariable Integer noIdentification) {
        log.debug("REST request to get all PedidoCabeceras by Client by noIdentification");
        if (pedidoCabeceraServiceExtended.findAllByClient(noIdentification).isEmpty()) {
            throw new NullPointerException("no Pedido Cabecera found for given noIdentification : "+noIdentification);
        }
        return pedidoCabeceraServiceExtended.findAllByClient(noIdentification);
    }

    @GetMapping("/pedido-cabeceras/{noIdentification}/{date}")
    public List<PedidoCabecera> getPedidoCabeceraByClienteByDate(
        @RequestParam("noIdentification") Integer noIdentification, @RequestParam("date") String date) {
        log.debug("REST request to get all PedidoCabeceras by Client by noIdentification by fechaCreacion date");

        LocalDate localDate = LocalDate.parse(date);

        if (pedidoCabeceraServiceExtended.findAllByClientAndFechaCreacion(noIdentification, localDate).isEmpty()) {
            throw new NullPointerException("no Pedido Cabecera found for given noIdentification : "+noIdentification
            +" and given date : "+localDate);
        }

        return pedidoCabeceraServiceExtended.findAllByClientAndFechaCreacion(noIdentification, localDate);
    }

}
