package com.gmarquezp.back.springbootbackclientes.models.dao;

import com.gmarquezp.back.springbootbackclientes.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductoDao extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %?1%")
    public List<Producto> findByNombreQuery(String nombre);

    // Usando el naming query
    public List<Producto> findByNombreContainingIgnoreCase(String nombre);
    public List<Producto> findByNombreStartingWithIgnoreCase(String nombre);

}
