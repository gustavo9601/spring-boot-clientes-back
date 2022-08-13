package com.gmarquezp.back.springbootbackclientes.models.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final Logger log = Logger.getLogger(UploadFileServiceImpl.class.getName());
    private final static String UPLOAD_FOLDER = "uploads";


    @Override
    public Resource cargar(String nombreArchivo) throws MalformedURLException {
        Path rutaArchivo = this.getPath(nombreArchivo);
        Resource recurso = null;

        recurso = new UrlResource(rutaArchivo.toUri());


        // Validando que sea posible leer
        if (!recurso.exists() && !recurso.isReadable()) {
            throw new RuntimeException("Error: No se pudo leer la foto");
        }

        return recurso;
    }

    @Override
    public String guardar(MultipartFile archivo) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();

        log.info("nombreArchivo=\t" + nombreArchivo);

        // Paths.get(// Si directorio de sistema debe ir ruta relativa "c// | \opt\etc")
        Path rutaArchivo = this.getPath(nombreArchivo);
        // Copia y mueve el archivo a la ruta escogida
        // StandardCopyOption.REPLACE_EXISTING // Si existe lo reemplaza
        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
        return nombreArchivo;
    }

    @Override
    public boolean eliminar(String nombreArchivo) {
        Path rutaArchivoAnterior = this.getPath(nombreArchivo);
        System.out.println("Eliminando archivo:\t" + rutaArchivoAnterior);
        try {
            Files.deleteIfExists(rutaArchivoAnterior);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path getPath(String nombreArchivo) {
        return Paths.get(UPLOAD_FOLDER)
                .resolve(nombreArchivo)
                .toAbsolutePath();
    }
}
