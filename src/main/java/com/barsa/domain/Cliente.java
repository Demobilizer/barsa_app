package com.barsa.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_doc")
    private String tipoDoc;

    @Column(name = "no_identificacion")
    private Integer noIdentificacion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "celular")
    private String celular;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "cliente")
    private Set<PedidoCabecera> pedidoCabeceras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public Cliente tipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
        return this;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public Integer getNoIdentificacion() {
        return noIdentificacion;
    }

    public Cliente noIdentificacion(Integer noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
        return this;
    }

    public void setNoIdentificacion(Integer noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Cliente apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public Cliente direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public Cliente celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<PedidoCabecera> getPedidoCabeceras() {
        return pedidoCabeceras;
    }

    public Cliente pedidoCabeceras(Set<PedidoCabecera> pedidoCabeceras) {
        this.pedidoCabeceras = pedidoCabeceras;
        return this;
    }

    public Cliente addPedidoCabecera(PedidoCabecera pedidoCabecera) {
        this.pedidoCabeceras.add(pedidoCabecera);
        pedidoCabecera.setCliente(this);
        return this;
    }

    public Cliente removePedidoCabecera(PedidoCabecera pedidoCabecera) {
        this.pedidoCabeceras.remove(pedidoCabecera);
        pedidoCabecera.setCliente(null);
        return this;
    }

    public void setPedidoCabeceras(Set<PedidoCabecera> pedidoCabeceras) {
        this.pedidoCabeceras = pedidoCabeceras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", tipoDoc='" + getTipoDoc() + "'" +
            ", noIdentificacion=" + getNoIdentificacion() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", celular='" + getCelular() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
