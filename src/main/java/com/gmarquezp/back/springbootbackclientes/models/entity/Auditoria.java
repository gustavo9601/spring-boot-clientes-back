package com.gmarquezp.back.springbootbackclientes.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class Auditoria {
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP) // Para que se guarde en formato de fecha y hora
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") // Patron que se visualizara al retornar JSON
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date updatedAt;



    /*
     * Eventos que se ejecutan en el ciclo de vida de la entidad
     * Antes o despues de persistirce el objeto
     * */

    @PrePersist
    public void prePersist() {
        Date currentDate = new Date();
        this.setCreatedAt(currentDate);
        this.setUpdatedAt(currentDate);
    }

    @PreUpdate
    public void preUpdate() {
        Date currentDate = new Date();
        this.setUpdatedAt(currentDate);
    }

    @PreRemove
    public void preRemove() {
        System.out.println("Se elimino el objeto=\t" + this.toString());
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
