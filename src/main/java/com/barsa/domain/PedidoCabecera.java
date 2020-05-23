package com.barsa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A PedidoCabecera.
 */
@Entity
@Table(name = "pedido_cabecera")
public class PedidoCabecera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_entrega")
    private Instant fechaEntrega;

    @Column(name = "pedido_numero")
    private Integer pedidoNumero;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "facturado")
    private Boolean facturado;

    @Column(name = "entregado")
    private Boolean entregado;

    @Column(name = "total_bruto", precision = 21, scale = 2)
    private BigDecimal totalBruto;

    @Column(name = "total_iva", precision = 21, scale = 2)
    private BigDecimal totalIva;

    @Column(name = "total_imp_consumo", precision = 21, scale = 2)
    private BigDecimal totalImpConsumo;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "pedidoCabecera")
    private Set<PedidoDetalle> pedidoDetalles = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("pedidoCabeceras")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public PedidoCabecera fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaEntrega() {
        return fechaEntrega;
    }

    public PedidoCabecera fechaEntrega(Instant fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public void setFechaEntrega(Instant fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getPedidoNumero() {
        return pedidoNumero;
    }

    public PedidoCabecera pedidoNumero(Integer pedidoNumero) {
        this.pedidoNumero = pedidoNumero;
        return this;
    }

    public void setPedidoNumero(Integer pedidoNumero) {
        this.pedidoNumero = pedidoNumero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public PedidoCabecera descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isFacturado() {
        return facturado;
    }

    public PedidoCabecera facturado(Boolean facturado) {
        this.facturado = facturado;
        return this;
    }

    public void setFacturado(Boolean facturado) {
        this.facturado = facturado;
    }

    public Boolean isEntregado() {
        return entregado;
    }

    public PedidoCabecera entregado(Boolean entregado) {
        this.entregado = entregado;
        return this;
    }

    public void setEntregado(Boolean entregado) {
        this.entregado = entregado;
    }

    public BigDecimal getTotalBruto() {
        return totalBruto;
    }

    public PedidoCabecera totalBruto(BigDecimal totalBruto) {
        this.totalBruto = totalBruto;
        return this;
    }

    public void setTotalBruto(BigDecimal totalBruto) {
        this.totalBruto = totalBruto;
    }

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public PedidoCabecera totalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
        return this;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

    public BigDecimal getTotalImpConsumo() {
        return totalImpConsumo;
    }

    public PedidoCabecera totalImpConsumo(BigDecimal totalImpConsumo) {
        this.totalImpConsumo = totalImpConsumo;
        return this;
    }

    public void setTotalImpConsumo(BigDecimal totalImpConsumo) {
        this.totalImpConsumo = totalImpConsumo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public PedidoCabecera total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<PedidoDetalle> getPedidoDetalles() {
        return pedidoDetalles;
    }

    public PedidoCabecera pedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
        return this;
    }

    public PedidoCabecera addPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.add(pedidoDetalle);
        pedidoDetalle.setPedidoCabecera(this);
        return this;
    }

    public PedidoCabecera removePedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.remove(pedidoDetalle);
        pedidoDetalle.setPedidoCabecera(null);
        return this;
    }

    public void setPedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public PedidoCabecera cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoCabecera)) {
            return false;
        }
        return id != null && id.equals(((PedidoCabecera) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PedidoCabecera{" +
            "id=" + getId() +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", pedidoNumero=" + getPedidoNumero() +
            ", descripcion='" + getDescripcion() + "'" +
            ", facturado='" + isFacturado() + "'" +
            ", entregado='" + isEntregado() + "'" +
            ", totalBruto=" + getTotalBruto() +
            ", totalIva=" + getTotalIva() +
            ", totalImpConsumo=" + getTotalImpConsumo() +
            ", total=" + getTotal() +
            "}";
    }
}
