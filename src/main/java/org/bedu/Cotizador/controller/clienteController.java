package org.bedu.Cotizador.controller;

import java.util.List;

import org.bedu.Cotizador.dto.clienteDTO;
import org.bedu.Cotizador.dto.createDTO.createClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.updateClienteDTO;
import org.bedu.Cotizador.service.clienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Endpoints de clientes", description = "CRUD de clientes")
@RestController
@Slf4j
@RequestMapping("/clientes")
@CrossOrigin("http://127.0.0.1:5500/")
public class clienteController {

    private final clienteService service;

    @Autowired
    public clienteController(clienteService service) {
        this.service = service;
    }

    @Operation(summary = "Obtiene una lista de todos los clientes")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<clienteDTO> findAll() {
        return service.findAll();
    }

    // Crear cliente
    @Operation(summary = "Crea un nuevo cliente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public clienteDTO save(@Valid @RequestBody createClienteDTO data) {
        log.info("Ejecutando guardado de un cliente");
        log.info(data.toString());
        return service.save(data);
    }

    // Actualizar
    @Operation(summary = "Sustituir datos del cliente por Id")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public clienteDTO update(@PathVariable long id, @Valid @RequestBody updateClienteDTO data) {
        log.info("Ejecutando actualización de un cliente con id: {}", id);
        log.info(data.toString());
        return service.update(data, id);
    }

    // Obtener por ID
    @Operation(summary = "Obtiene un cliente por Id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public clienteDTO findById(@PathVariable long id) {
        log.info("Obteniendo información de un cliente con id: {}", id);
        return service.findById(id);
    }

    // Eliminar
    @Operation(summary = "Eliminar un cliente por Id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        log.info("Ejecutando eliminación de un cliente con id: {}", id);
        service.delete(id);
    }
}