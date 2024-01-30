package org.bedu.Cotizador.controller;

import java.util.List;

import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.createDTO.CreateClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateClienteDTO;
import org.bedu.Cotizador.service.ClienteService;
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
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Operation(summary = "Obtiene una lista de todos los clientes")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> findAll() {
        return service.findAll();
    }

    // Crear Cliente
    @Operation(summary = "Crea un nuevo cliente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO save(@Valid @RequestBody CreateClienteDTO data) {
        log.info("Ejecutando guardado de un Cliente");
        log.info(data.toString());
        return service.save(data);
    }

    // Actualizar
    @Operation(summary = "Sustituir datos del cliente por Id")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO update(@PathVariable long id, @Valid @RequestBody UpdateClienteDTO data) {
        log.info("Ejecutando actualización de un Cliente con id: {}", id);
        log.info(data.toString());
        return service.update(data, id);
    }

    // Obtener por ID
    @Operation(summary = "Obtiene un cliente por Id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO findById(@PathVariable long id) {
        log.info("Obteniendo información de un Cliente con id: {}", id);
        return service.findById(id);
    }

    // Eliminar
    @Operation(summary = "Eliminar un cliente por Id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        log.info("Ejecutando eliminación de un Cliente con id: {}", id);
        service.delete(id);
    }
}
