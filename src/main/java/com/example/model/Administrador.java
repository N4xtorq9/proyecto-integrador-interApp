package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidades
// Las clases siempre van en singular
// @table siempre va en plural
@Entity
@Table(name = "Administradores")
public class Administrador  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdministrador;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private String rol;
    private String estado;
    private String fecharegistro;
    private String clave;

    // ===================
    // Constructores
    // ===================
    public Administrador() {
    }

    public Administrador(String nombre, String contacto, String telefono, String email, String direccion) {
        this(null, nombre, contacto, telefono, email, direccion);
    }

    public Administrador(Long idAdministrador, String nombre, String contacto, String telefono, String email, String direccion) {
        this.idAdministrador = idAdministrador;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.rol = rol;
        this.estado = estado;
        this.fecharegistro = fecharegistro;
        this.clave = clave;
    }

    // ===================
    // Getters y setters
    // ===================
    public Long getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Long idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getRol() {
        return rol;
    }

    public String getEstado() {
        return estado;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public String getClave() {
        return clave;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Administrador [idAdministrador=" + idAdministrador + ", nombre=" + nombre + ", contacto=" + contacto + "]";
    }
}
