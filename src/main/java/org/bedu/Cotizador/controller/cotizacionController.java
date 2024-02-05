package org.bedu.Cotizador.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.bedu.Cotizador.dto.cotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.createCotizacionDTO;
import org.bedu.Cotizador.service.cotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

    
@Tag(name = "Endpoints de cotizacion", description = "Crear cotizacion")
@Slf4j
@RestController
@RequestMapping("/cotizaciones")
@CrossOrigin("http://127.0.0.1:5500/")
public class cotizacionController {


    private final cotizacionService service;

    @Autowired
    public cotizacionController(cotizacionService service) {
        this.service = service;
    }

    @Operation(summary = "Crear cotizacion")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public cotizacionDTO crearCotizacion (@Valid @RequestBody createCotizacionDTO data){
        log.info("Creando nueva cotizacion");
        log.info(data.toString());
        return service.crearCotizacion(data);
    }
}