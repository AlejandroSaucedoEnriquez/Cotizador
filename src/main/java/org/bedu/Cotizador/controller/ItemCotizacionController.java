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

@Tag(name = "Endpoints de itemCotizacion", description = "Agregar varios productos a Cotizacion")
@RestController
@RequestMapping("/api/item-Cotizacion")
@CrossOrigin("http://127.0.0.1:5500/")
public class ItemCotizacionController {

    private final ItemCotizacionService itemCotizacionService;
    @Autowired
    public ItemCotizacionController(ItemCotizacionService itemCotizacionService) {
        this.itemCotizacionService = itemCotizacionService;
    }

    @Operation(summary = "Agregar items varios productos a Cotizacion")
        @PostMapping("/agregar/{cotizacionId}")
        public ResponseEntity<ItemCotizacionDTO> agregarItemCotizacion(
                @PathVariable Long cotizacionId,
                @RequestBody @Valid CreateItemCotizacionDTO createItemCotizacionDTO) {

            ItemCotizacionDTO nuevoItemCotizacion = itemCotizacionService.addItemCotizacion(createItemCotizacionDTO, cotizacionId);

           return new ResponseEntity<>(nuevoItemCotizacion, HttpStatus.CREATED);
        }
}