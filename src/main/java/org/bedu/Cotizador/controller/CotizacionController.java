package org.bedu.Cotizador.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

    
@Tag(name = "Endpoints de Cotizacion", description = "Crear Cotizacion")
@Slf4j
@RestController
@RequestMapping("/cotizaciones")
@CrossOrigin("http://127.0.0.1:5500/")
public class CotizacionController {


    private final CotizacionService service;

    @Autowired
    public CotizacionController(CotizacionService service) {
        this.service = service;
    }

    @Operation(summary = "Crear Cotizacion")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CotizacionDTO crearCotizacion (@Valid @RequestBody CreateCotizacionDTO data){
        log.info("Creando nueva Cotizacion");
        log.info(data.toString());
        return service.crearCotizacion(data);
    }

    @Operation(summary = "Obtener cotizacion por id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CotizacionDTO obtenerCotizacion(@PathVariable Long id) {
        return service.obtenerCotizacionDTO(id);
    }
}