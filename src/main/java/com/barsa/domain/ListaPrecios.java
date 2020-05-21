package com.barsa.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;

/**
 * A ListaPrecios.
 */
@Entity
@Table(name = "lista_precios")
public class ListaPrecios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "porcentaje", precision = 21, scale = 2)
    private BigDecimal porcentaje;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ListaPrecios descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public ListaPrecios porcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
        return this;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public ListaPrecios valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListaPrecios)) {
            return false;
        }
        return id != null && id.equals(((ListaPrecios) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ListaPrecios{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", porcentaje=" + getPorcentaje() +
            ", valor=" + getValor() +
            "}";
    }
}
