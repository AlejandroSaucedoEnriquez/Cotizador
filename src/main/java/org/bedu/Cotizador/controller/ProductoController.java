package org.bedu.Cotizador.controller;

import java.util.List;

import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateProductoDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateProductoDTO;
import org.bedu.Cotizador.service.ProductoService;
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
public class ProductoController {

    private final ProductoService service;
    @Autowired
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Operation(summary = "Obtiene una lista de todos los productos")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoDTO> findAll(){
        return service.findAll();
    }

    //Crear un Producto
    @Operation(summary = "Crea un nuevo Producto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDTO save(@Valid @RequestBody CreateProductoDTO data){
    log.info("Ejecutando guardado de un Producto");
    log.info(data.toString());
    return service.save(data);
    }

    // Actualizar un Producto por ID
    @Operation(summary = "Sustituir datos del Producto por Id")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductoDTO update(@PathVariable Long id, @Valid @RequestBody UpdateProductoDTO data) {
        log.info("Ejecutando actualización de un Producto con id: {}", id);
        log.info(data.toString());
        return service.update(data, id);
    }

    // Obtener un Producto por ID
    @Operation(summary = "Obtiene un Producto por Id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductoDTO findById(@PathVariable Long id) {
        log.info("Obteniendo información de un Producto con id: {}", id);
        return service.findById(id);
    }

    // Eliminar un Producto por ID
    @Operation(summary = "Eliminar un Producto por Id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Ejecutando eliminación de un Producto con id: {}", id);
        service.delete(id);
    }
}
