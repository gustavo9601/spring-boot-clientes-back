package com.gmarquezp.back.springbootbackclientes.controllers;

import com.gmarquezp.back.springbootbackclientes.models.entity.Factura;
import com.gmarquezp.back.springbootbackclientes.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = {"http://localhost:4200"}) // Cors manual

public class FacturaRestController {

    @Autowired
    IClienteService clienteService;

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Factura show(@PathVariable(name = "id") Long id){
        return this.clienteService.findFacturaById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id){
        this.clienteService.deleteFacturaById(id);
    }


}
