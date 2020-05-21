package com.barsa.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.barsa.domain.Producto} entity. This class is used
 * in {@link com.barsa.web.rest.ProductoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /productos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter descripcion;

    private StringFilter unidad;

    private IntegerFilter cantidad;

    private BigDecimalFilter precio;

    private BigDecimalFilter iva;

    private BigDecimalFilter icovalor;

    private LongFilter pedidoDetalleId;

    public ProductoCriteria() {
    }

    public ProductoCriteria(ProductoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.unidad = other.unidad == null ? null : other.unidad.copy();
        this.cantidad = other.cantidad == null ? null : other.cantidad.copy();
        this.precio = other.precio == null ? null : other.precio.copy();
        this.iva = other.iva == null ? null : other.iva.copy();
        this.icovalor = other.icovalor == null ? null : other.icovalor.copy();
        this.pedidoDetalleId = other.pedidoDetalleId == null ? null : other.pedidoDetalleId.copy();
    }

    @Override
    public ProductoCriteria copy() {
        return new ProductoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodigo() {
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public StringFilter getUnidad() {
        return unidad;
    }

    public void setUnidad(StringFilter unidad) {
        this.unidad = unidad;
    }

    public IntegerFilter getCantidad() {
        return cantidad;
    }

    public void setCantidad(IntegerFilter cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimalFilter getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimalFilter precio) {
        this.precio = precio;
    }

    public BigDecimalFilter getIva() {
        return iva;
    }

    public void setIva(BigDecimalFilter iva) {
        this.iva = iva;
    }

    public BigDecimalFilter getIcovalor() {
        return icovalor;
    }

    public void setIcovalor(BigDecimalFilter icovalor) {
        this.icovalor = icovalor;
    }

    public LongFilter getPedidoDetalleId() {
        return pedidoDetalleId;
    }

    public void setPedidoDetalleId(LongFilter pedidoDetalleId) {
        this.pedidoDetalleId = pedidoDetalleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductoCriteria that = (ProductoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(unidad, that.unidad) &&
            Objects.equals(cantidad, that.cantidad) &&
            Objects.equals(precio, that.precio) &&
            Objects.equals(iva, that.iva) &&
            Objects.equals(icovalor, that.icovalor) &&
            Objects.equals(pedidoDetalleId, that.pedidoDetalleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        codigo,
        descripcion,
        unidad,
        cantidad,
        precio,
        iva,
        icovalor,
        pedidoDetalleId
        );
    }

    @Override
    public String toString() {
        return "ProductoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codigo != null ? "codigo=" + codigo + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (unidad != null ? "unidad=" + unidad + ", " : "") +
                (cantidad != null ? "cantidad=" + cantidad + ", " : "") +
                (precio != null ? "precio=" + precio + ", " : "") +
                (iva != null ? "iva=" + iva + ", " : "") +
                (icovalor != null ? "icovalor=" + icovalor + ", " : "") +
                (pedidoDetalleId != null ? "pedidoDetalleId=" + pedidoDetalleId + ", " : "") +
            "}";
    }

}
