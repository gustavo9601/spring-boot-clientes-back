package com.gmarquezp.back.springbootbackclientes.controllers;

import com.gmarquezp.back.springbootbackclientes.models.entity.Cliente;
import com.gmarquezp.back.springbootbackclientes.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.logging.Logger;
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

    private final Logger log = Logger.getLogger(ClienteRestController.class.getName());

    @GetMapping("")
    public List<Cliente> index() {
        return this.clienteService.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page) {
        return this.clienteService.findAll(PageRequest.of(page, 4));
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

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(name = "archivo") MultipartFile archivo,
                                        @RequestParam(name = "id") Long id) {
        Cliente cliente = this.clienteService.findById(id);

        Map<String, Object> response = new HashMap<>();

        if (!archivo.isEmpty()) {
            String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();

            log.info("nombreArchivo=\t" + nombreArchivo);

            // Paths.get(// Si directorio de sistema debe ir ruta relativa "c// | \opt\etc")
            Path rutaArchivo = Paths.get("uploads")
                    .resolve(nombreArchivo)
                    .toAbsolutePath();

            try {
                // Copia y mueve el archivo a la ruta escogida
                // StandardCopyOption.REPLACE_EXISTING // Si existe lo reemplaza
                Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir el archivo con nombre:\t" + nombreArchivo);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }


            // Borrando archivo anterior
            if (cliente.getFoto() != null && !cliente.getFoto().isEmpty()) {
                Path rutaArchivoAnterior = Paths.get("uploads")
                        .resolve(cliente.getFoto())
                        .toAbsolutePath();
                try {
                    System.out.println("Eliminando archivo:\t" + rutaArchivoAnterior);
                    Files.deleteIfExists(rutaArchivoAnterior);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            cliente.setFoto(nombreArchivo);
            this.clienteService.save(cliente);

            response.put("cliente", cliente);
            response.put("mensaje", "El archivo con nombre: |" + nombreArchivo + "| ha sido cargado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }

        response.put("mensaje", "No se subio ningun archivo =(");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/upload/{nombreFoto:.+}") // :.+ // para que no trunque la extension
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
        Path rutaArchivo = Paths.get("uploads")
                .resolve(nombreFoto)
                .toAbsolutePath();
        Resource recurso = null;
        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error: No se pudo cargar la foto");
        }

        // Validando que sea posible leer
        if (!recurso.exists() && recurso.isReadable()) {
            throw new RuntimeException("Error: No se pudo leer la foto");
        }

        // Cabeceras para que permita la descarga
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

        log.info("recurso.getFilename()=\t" + recurso.getFilename());
        
        // Si no se agrega cabecera, retornara el recurso en binario
        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable Long id) {

        Cliente cliente = this.clienteService.findById(id);
        // Borrando imagen de tener anterior
        if (cliente.getFoto() != null && !cliente.getFoto().isEmpty()) {
            Path rutaArchivoAnterior = Paths.get("uploads")
                    .resolve(cliente.getFoto())
                    .toAbsolutePath();
            try {
                System.out.println("Eliminando archivo:\t" + rutaArchivoAnterior);
                Files.deleteIfExists(rutaArchivoAnterior);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.clienteService.delete(id);
    }
}
