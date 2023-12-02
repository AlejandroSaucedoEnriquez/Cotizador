package org.bedu.Cotizador.controller;

import java.util.List;

import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.service.ClienteService;
import org.bedu.Cotizador.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cotizacion")
public class CotizacionController {
    @Autowired
    private CotizacionService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CotizacionDTO> findAll(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO save(@Valid @RequestBody CreateCotizacionDTO data){
    return service.save(data);
    }
}
