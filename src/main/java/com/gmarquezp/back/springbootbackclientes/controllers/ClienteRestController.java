package com.gmarquezp.back.springbootbackclientes.controllers;

import com.gmarquezp.back.springbootbackclientes.models.entity.Cliente;
import com.gmarquezp.back.springbootbackclientes.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    // @ResponseStatus(HttpStatus.CREATED) // 201
    /*
     * @Valid // Se valida el objeto que se recibe
     * @RequestBody // Se recibe el objeto en el body
     * BindingResult // Contiene la validacion sobre el objeto
     * */

    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
        System.out.println("cliente = " + cliente);


        Cliente nuevoCliente = null;
        Map<String, Object> response = new HashMap<>();


        if (result.hasErrors()) {
            System.out.println("Error en el cliente");


            /*
            // Forma #1
            List<String> errors = new ArrayList<>();

            result.getFieldErrors().forEach(error -> {
                errors.add("El campo: [" + error.getField() + "] :\t" + error.getDefaultMessage());
            });
            */

           /*
           Forma #2

           List<String> errors = result.getFieldErrors().stream()
                    .map((error) -> {
                        return "El campo: [" + error.getField() + "] :\t" + error.getDefaultMessage();
                    }).collect(Collectors.toList());*/

            Map<String, List<String>> erroresMap = new HashMap<>();

            result.getFieldErrors()
                    .forEach((error) -> {
                        if (erroresMap.containsKey(error.getField())) {
                            erroresMap.get(error.getField()).add(error.getDefaultMessage());
                        } else {
                            erroresMap.put(error.getField(), new ArrayList<>(List.of(error.getDefaultMessage())));
                        }
                    });

            response.put("errors", erroresMap);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }


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
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,
                                    BindingResult result,
                                    @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {


/*            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map((error) -> {
                        return "El campo: [" + error.getField() + "] :\t" + error.getDefaultMessage();
                    }).collect(Collectors.toList());
            */

            Map<String, List<String>> erroresMap = new HashMap<>();

            result.getFieldErrors()
                            .forEach((error) -> {
                                if (erroresMap.containsKey(error.getField())) {
                                    erroresMap.get(error.getField()).add(error.getDefaultMessage());
                                } else {
                                    erroresMap.put(error.getField(), new ArrayList<>(List.of(error.getDefaultMessage())));
                                }
                            });


            response.put("errors", erroresMap);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Cliente clienteActual = this.clienteService.findById(id);
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            this.clienteService.save(clienteActual);

            response.put("mensaje", "El cliente ha sido actualizado con éxito");
            response.put("cliente", clienteActual);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        } catch (DataAccessException exception) {
            response.put("mensaje", "Error al realizar el update en la base de datos");
            response.put("error", exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable Long id) {
        this.clienteService.delete(id);
    }
}
