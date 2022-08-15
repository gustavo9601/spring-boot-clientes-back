package com.gmarquezp.back.springbootbackclientes.models.services;

import com.gmarquezp.back.springbootbackclientes.models.entity.Usuario;

public interface IUsuarioService {
    public Usuario findByUsername(String username);
}
