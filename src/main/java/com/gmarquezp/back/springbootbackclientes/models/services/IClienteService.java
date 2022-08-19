package com.gmarquezp.back.springbootbackclientes.models.services;

import com.gmarquezp.back.springbootbackclientes.models.entity.Cliente;
import com.gmarquezp.back.springbootbackclientes.models.entity.Factura;
import com.gmarquezp.back.springbootbackclientes.models.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);

    public Cliente findById(Long id);
    public Cliente save(Cliente cliente);

    public void delete(Long id);

    public List<Region> findAllRegions();

    public Factura findFacturaById(Long id);
    public Factura saveFactura(Factura factura);
    public void deleteFacturaById(Long id);

}
