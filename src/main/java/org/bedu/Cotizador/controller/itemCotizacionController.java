package org.bedu.Cotizador.controller;

import jakarta.validation.Valid;

import org.bedu.Cotizador.dto.itemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.createItemCotizacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endpoints de itemCotizacion", description = "Agregar varios productos a cotizacion")
@RestController
@RequestMapping("/api/item-cotizacion")
@CrossOrigin("http://127.0.0.1:5500/")
public class itemCotizacionController {

    private final org.bedu.Cotizador.service.itemCotizacionService itemCotizacionService;
    @Autowired
    public itemCotizacionController(org.bedu.Cotizador.service.itemCotizacionService itemCotizacionService) {
        this.itemCotizacionService = itemCotizacionService;
    }

    @Operation(summary = "Agregar items varios productos a cotizacion")
        @PostMapping("/agregar/{cotizacionId}")
        public ResponseEntity<itemCotizacionDTO> agregarItemCotizacion(
                @PathVariable Long cotizacionId,
                @RequestBody @Valid createItemCotizacionDTO createItemCotizacionDTO) {

            itemCotizacionDTO nuevoItemCotizacion = itemCotizacionService.addItemCotizacion(createItemCotizacionDTO, cotizacionId);

           return new ResponseEntity<>(nuevoItemCotizacion, HttpStatus.CREATED);
        }
}