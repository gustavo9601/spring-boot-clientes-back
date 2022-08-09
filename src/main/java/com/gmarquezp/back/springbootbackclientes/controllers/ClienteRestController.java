package com.gmarquezp.back.springbootbackclientes.controllers;

import com.gmarquezp.back.springbootbackclientes.models.entity.Cliente;
import com.gmarquezp.back.springbootbackclientes.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
// Se configuro desde la clase CorsConfiguration
// Habilitando cors desde los origenes
// Se puede configurar los metodos, age, headers, etc.
// @CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("")
    public List<Cliente> index() {
        return this.clienteService.findAll();
    }


    /*
     * public ResponseEntity<?> // Clase que permite controlar la respuesta si ocurre algun error
     * */
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = this.clienteService.findById(id);
        } catch (DataAccessException exception) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put("mensaje", "El cliente con el id: ".concat(id.toString()).concat(" no existe =("));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<?> create(@RequestBody Cliente cliente) {
        System.out.println("cliente = " + cliente);

        Cliente nuevoCliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            nuevoCliente = this.clienteService.save(cliente);
        } catch (DataAccessException exception) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido creado con éxito");
        response.put("cliente", nuevoCliente);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente clienteActual = this.clienteService.findById(id);
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setEmail(cliente.getEmail());

        return this.clienteService.save(clienteActual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable Long id) {
        this.clienteService.delete(id);
    }
}
