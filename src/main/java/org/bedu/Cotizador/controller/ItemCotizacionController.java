package org.bedu.Cotizador.controller;

import jakarta.validation.Valid;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.service.ItemCotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item-cotizacion")
public class ItemCotizacionController {

        @Autowired
        private ItemCotizacionService itemCotizacionService;

        @PostMapping("/agregar/{cotizacionId}")
        public ResponseEntity<ItemCotizacionDTO> agregarItemCotizacion(
                @PathVariable Long cotizacionId,
                @RequestBody @Valid CreateItemCotizacionDTO createItemCotizacionDTO) {

            ItemCotizacionDTO nuevoItemCotizacion = itemCotizacionService.addItemCotizacion(createItemCotizacionDTO, cotizacionId);

            return new ResponseEntity<>(nuevoItemCotizacion, HttpStatus.CREATED);
        }
    }

