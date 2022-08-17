package com.gmarquezp.back.springbootbackclientes.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;

    // Muchos items pueden tener un producto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id") // Llave foranea en facturas_items
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Para ignorar propiedads de la relacion inversa en el JSON

    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImporte(){
        return this.getCantidad() * this.producto.getPrecio();
    }

    // serializable
    private static final long serialVersionUID = 1L;
}
