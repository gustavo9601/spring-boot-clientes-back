package com.gmarquezp.back.springbootbackclientes.models.services;

import com.gmarquezp.back.springbootbackclientes.models.entity.Cliente;

import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();

}
