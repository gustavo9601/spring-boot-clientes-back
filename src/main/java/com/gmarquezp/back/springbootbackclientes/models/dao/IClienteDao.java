package com.gmarquezp.back.springbootbackclientes.models.dao;

import com.gmarquezp.back.springbootbackclientes.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IClienteDao extends JpaRepository<Cliente, Long> {
    // Query clientes with regiones
    @Query("select c from Cliente c join fetch c.region")
    public List<Cliente> findAllWithRegiones();
}
