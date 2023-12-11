package org.bedu.Cotizador.controller;

import jakarta.validation.Valid;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.service.ItemCotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endpoints de itemCotizacion", description = "Agregar varios productos a cotizacion")
@RestController
@RequestMapping("/api/item-cotizacion")
public class ItemCotizacionController {

        @Autowired
        private ItemCotizacionService itemCotizacionService;
        
        @Operation(summary = "Agregar items varios productos a cotizacion")
        @PostMapping("/agregar/{cotizacionId}")
        public ResponseEntity<ItemCotizacionDTO> agregarItemCotizacion(
                @PathVariable Long cotizacionId,
                @RequestBody @Valid CreateItemCotizacionDTO createItemCotizacionDTO) {

            ItemCotizacionDTO nuevoItemCotizacion = itemCotizacionService.addItemCotizacion(createItemCotizacionDTO, cotizacionId);

            return new ResponseEntity<>(nuevoItemCotizacion, HttpStatus.CREATED);
        }
    }

