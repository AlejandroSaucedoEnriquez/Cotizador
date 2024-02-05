package org.bedu.Cotizador.controller;

import java.util.List;

import org.bedu.Cotizador.dto.productoDTO;
import org.bedu.Cotizador.dto.createDTO.createProductoDTO;
import org.bedu.Cotizador.dto.updateDTO.updateProductoDTO;
import org.bedu.Cotizador.service.productoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Endpoints de productos", description = "CRUD de productos")
@RestController
@Slf4j
@RequestMapping("/productos")
@CrossOrigin("http://127.0.0.1:5500/")
public class productoController {

    private final productoService service;
    @Autowired
    public productoController(productoService service) {
        this.service = service;
    }

    @Operation(summary = "Obtiene una lista de todos los productos")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<productoDTO> findAll(){
        return service.findAll();
    }

    //Crear un producto
    @Operation(summary = "Crea un nuevo producto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public productoDTO save(@Valid @RequestBody createProductoDTO data){
    log.info("Ejecutando guardado de un producto");
    log.info(data.toString());
    return service.save(data);
    }

    // Actualizar un producto por ID
    @Operation(summary = "Sustituir datos del producto por Id")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public productoDTO update(@PathVariable Long id, @Valid @RequestBody updateProductoDTO data) {
        log.info("Ejecutando actualización de un producto con id: {}", id);
        log.info(data.toString());
        return service.update(data, id);
    }

    // Obtener un producto por ID
    @Operation(summary = "Obtiene un producto por Id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public productoDTO findById(@PathVariable Long id) {
        log.info("Obteniendo información de un producto con id: {}", id);
        return service.findById(id);
    }

    // Eliminar un producto por ID
    @Operation(summary = "Eliminar un producto por Id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Ejecutando eliminación de un producto con id: {}", id);
        service.delete(id);
    }
}
