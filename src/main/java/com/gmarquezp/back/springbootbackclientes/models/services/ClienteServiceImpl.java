package com.gmarquezp.back.springbootbackclientes.models.services;

import com.gmarquezp.back.springbootbackclientes.models.dao.IClienteDao;
import com.gmarquezp.back.springbootbackclientes.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {
    @Autowired
    private IClienteDao clienteDao;

    // Permite manejar transaccionalidad
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return this.clienteDao.findAll();
    }
}
