package com.gmarquezp.back.springbootbackclientes.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "nombre")
    @NotEmpty // No puede estar vacio
    @Size(min = 3, max = 50) // Tamaño minimo y maximo
    private String nombre;

    @NotEmpty(message = "{validation.apellido.NotEmpty}") // Uamos el mensaje de ValidationMessages.properties, configurando  en MvcConfig
    private String apellido;
    @Column(unique = true, nullable = false, name = "email")
    @Email // Formato de email
    private String email;
    @Embedded
    private Auditoria auditoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toLowerCase().trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido.toLowerCase().trim();
        ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase().trim();
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", auditoria=" + auditoria +
                '}';
    }
}
