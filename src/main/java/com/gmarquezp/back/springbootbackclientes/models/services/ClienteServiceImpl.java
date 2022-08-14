package com.gmarquezp.back.springbootbackclientes.models.services;

import com.gmarquezp.back.springbootbackclientes.models.dao.IClienteDao;
import com.gmarquezp.back.springbootbackclientes.models.dao.IRegionDao;
import com.gmarquezp.back.springbootbackclientes.models.entity.Cliente;
import com.gmarquezp.back.springbootbackclientes.models.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {
    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IRegionDao regionDao;

    // Permite manejar transaccionalidad
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return this.clienteDao.findAllWithRegiones();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return this.clienteDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)

    public Cliente findById(Long id) {
        return this.clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return this.clienteDao.save(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.clienteDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAllRegions() {
        return this.regionDao.findAllRegiones();
    }
}
