package com.barsa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;

/**
 * A PedidoDetalle.
 */
@Entity
@Table(name = "pedido_detalle")
public class PedidoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "posicion")
    private Integer posicion;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JsonIgnoreProperties("pedidoDetalles")
    private PedidoCabecera pedidoNumero;

    @ManyToOne
    @JsonIgnoreProperties("pedidoDetalles")
    private Producto articuloCodigo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public PedidoDetalle posicion(Integer posicion) {
        this.posicion = posicion;
        return this;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public PedidoDetalle cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public PedidoDetalle total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public PedidoCabecera getPedidoNumero() {
        return pedidoNumero;
    }

    public PedidoDetalle pedidoNumero(PedidoCabecera pedidoCabecera) {
        this.pedidoNumero = pedidoCabecera;
        return this;
    }

    public void setPedidoNumero(PedidoCabecera pedidoCabecera) {
        this.pedidoNumero = pedidoCabecera;
    }

    public Producto getArticuloCodigo() {
        return articuloCodigo;
    }

    public PedidoDetalle articuloCodigo(Producto producto) {
        this.articuloCodigo = producto;
        return this;
    }

    public void setArticuloCodigo(Producto producto) {
        this.articuloCodigo = producto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoDetalle)) {
            return false;
        }
        return id != null && id.equals(((PedidoDetalle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PedidoDetalle{" +
            "id=" + getId() +
            ", posicion=" + getPosicion() +
            ", cantidad=" + getCantidad() +
            ", total=" + getTotal() +
            "}";
    }
}
