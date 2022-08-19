package com.gmarquezp.back.springbootbackclientes.models.dao;

import com.gmarquezp.back.springbootbackclientes.models.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFacturaDao extends JpaRepository<Factura, Long> {
}
