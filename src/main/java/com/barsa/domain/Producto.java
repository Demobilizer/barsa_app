package com.barsa.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio", precision = 21, scale = 2)
    private BigDecimal precio;

    @Column(name = "iva", precision = 21, scale = 2)
    private BigDecimal iva;

    @Column(name = "icovalor", precision = 21, scale = 2)
    private BigDecimal icovalor;

    @OneToMany(mappedBy = "producto")
    private Set<PedidoDetalle> pedidoDetalles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Producto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Producto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Producto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public String getUnidad() {
        return unidad;
    }

    public Producto unidad(String unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Producto cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Producto precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public Producto iva(BigDecimal iva) {
        this.iva = iva;
        return this;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getIcovalor() {
        return icovalor;
    }

    public Producto icovalor(BigDecimal icovalor) {
        this.icovalor = icovalor;
        return this;
    }

    public void setIcovalor(BigDecimal icovalor) {
        this.icovalor = icovalor;
    }

    public Set<PedidoDetalle> getPedidoDetalles() {
        return pedidoDetalles;
    }

    public Producto pedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
        return this;
    }

    public Producto addPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.add(pedidoDetalle);
        pedidoDetalle.setProducto(this);
        return this;
    }

    public Producto removePedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.remove(pedidoDetalle);
        pedidoDetalle.setProducto(null);
        return this;
    }

    public void setPedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", unidad='" + getUnidad() + "'" +
            ", cantidad=" + getCantidad() +
            ", precio=" + getPrecio() +
            ", iva=" + getIva() +
            ", icovalor=" + getIcovalor() +
            "}";
    }
}
