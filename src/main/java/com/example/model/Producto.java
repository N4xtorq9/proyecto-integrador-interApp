package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private Long idUsuario;
    private String codigo;
    private String nombre;
    private String categoria;
    private Double estado;
    private Integer stock;

    // ===================
    // Constructores
    // ===================
    public Producto() {
    }

    public Producto(Long idUsuario, String codigo, String nombre, String categoria, Double estado, Integer stock) {
        this(null, idUsuario, codigo, nombre, categoria, estado, stock);
    }

    public Producto(Long idProducto, Long idUsuario, String codigo, String nombre, String categoria, Double estado, Integer stock) {
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.estado = estado;
        this.stock = stock;
    }

    public Producto(Integer int1, String value, String value2, String value3, String value4, String value5,
            Integer int2) {
        //TODO Auto-generated constructor stub
    }

    // ===================
    // Getters y setters
    // ===================
    public Long getidProducto() {
        return idProducto;
    }

    public void setidProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    

    public Long getidUsuario() {
        return idUsuario;
    }

    public void setidUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getestado() {
        return estado;
    }

    public void setestado(Double estado) {
        this.estado = estado;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto [idProducto=" + idProducto + ", idUsuario=" + idUsuario + ", codigo=" + codigo + ", nombre="
                + nombre + ", categoria=" + categoria + ", estado=" + estado + ", stock=" + stock + "]";
    }

    
}
