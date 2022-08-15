package com.gmarquezp.back.springbootbackclientes.models.dao;

import com.gmarquezp.back.springbootbackclientes.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

    // Naming Method
    public Usuario findByUsername(String username);

    // JPQL
    @Query("select u from Usuario u where u.username = ?1")
    public Usuario findByUsername2(String username);

    // Native Query
    @Query(value = "select * from usuario where username = ?1", nativeQuery = true)
    public Usuario findByUsername3(String username);



}
