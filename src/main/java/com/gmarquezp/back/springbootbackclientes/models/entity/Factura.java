package com.gmarquezp.back.springbootbackclientes.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DatabindException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String observacion;

    @Temporal(TemporalType.DATE)
    private Date fechaFactura;

    // Muchas facturas tienen un cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id") // Llave con la tabla clientes
    @JsonIgnoreProperties({"facturas", "hibernateLazyInitializer", "handler"})
    // Para ignorar propiedades de la relacion inversa en el JSON, para no se llame en un loop infinito
    private Cliente cliente;


    // Una factura tiene muchos items
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id") // Llave foranea en facturas_items
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    // Para ignorar propiedads de la relacion inversa en el JSON

    private List<ItemFactura> itemsFacturas;

    @Embedded
    private Auditoria auditoria;

    public Factura() {
        this.itemsFacturas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public List<ItemFactura> getItemsFacturas() {
        return itemsFacturas;
    }

    public void setItemsFacturas(List<ItemFactura> itemsFacturas) {
        this.itemsFacturas = itemsFacturas;
    }

    public Double getTotal() {
        return this.getItemsFacturas()
                .stream()
                .mapToDouble(ItemFactura::getImporte)
                .sum();
    }

    // serial version
    private static final long serialVersionUID = 1L;
}
