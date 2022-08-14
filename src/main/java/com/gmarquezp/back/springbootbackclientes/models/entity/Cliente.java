package com.gmarquezp.back.springbootbackclientes.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

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

    @NotEmpty(message = "{validation.apellido.NotEmpty}") // Usamos el mensaje de ValidationMessages.properties, configurando  en MvcConfig
    private String apellido;
    @Column(unique = true, nullable = false, name = "email")
    @Email // Formato de email
    private String email;

    @NotNull
    @JsonFormat(pattern = "yyyy/MM/dd") // Patron que se visualizara al retornar JSON
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Nullable
    private String foto;

    @Embedded
    private Auditoria auditoria;

    // una region puede tener muchos clientes, pero un cliente una region
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id") // Nombre de la columna en cliente
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Para que no se devuelva el objeto Region en el JSON
    private Region region;

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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Nullable
    public String getFoto() {
        return foto;
    }

    public void setFoto(@Nullable String foto) {
        this.foto = foto;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", auditoria=" + auditoria +
                ", foto=" + foto +
                '}';
    }
}
