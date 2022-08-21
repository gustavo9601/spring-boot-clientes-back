package com.gmarquezp.back.springbootbackclientes.controllers;

import com.gmarquezp.back.springbootbackclientes.models.entity.Factura;
import com.gmarquezp.back.springbootbackclientes.models.entity.Producto;
import com.gmarquezp.back.springbootbackclientes.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = {"http://localhost:4200"}) // Cors manual

public class FacturaRestController {

    @Autowired
    IClienteService clienteService;

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseStatus(code = HttpStatus.OK)
    public Factura show(@PathVariable(name = "id") Long id) {
        return this.clienteService.findFacturaById(id);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id) {
        this.clienteService.deleteFacturaById(id);
    }

    @GetMapping("/filtrar-productos/{nombre}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(code = HttpStatus.OK)
    public List<Producto> filtrarProductos(@PathVariable(name = "nombre") String nombre) {
        return this.clienteService.findProductoByNombre(nombre);
    }

    @PostMapping("")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public Factura save(@RequestBody Factura factura) {
        return this.clienteService.saveFactura(factura);
    }

}
