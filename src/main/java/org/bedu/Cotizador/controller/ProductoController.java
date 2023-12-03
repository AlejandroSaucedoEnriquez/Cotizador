package org.bedu.Cotizador.controller;

import java.util.List;

import org.bedu.Cotizador.dto.CreateProductoDTO;
import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoDTO> findAll(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDTO save(@Valid @RequestBody CreateProductoDTO data){
    log.info("Ejecutando guardado de un producto");
    log.info(data.toString());
    return service.save(data);
    }
}
