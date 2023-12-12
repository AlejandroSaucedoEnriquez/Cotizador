package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateItemCotizacionDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.*;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class CotizacionService {
    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemCotizacionService itemCotizacionService;

    @Autowired
    private CotizacionMapper cotizacionMapper;

    public CotizacionDTO crearCotizacion(CreateCotizacionDTO createCotizacionDTO) {
        // Recuperar el cliente
        Cliente cliente = clienteRepository.findById(createCotizacionDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        // Crear la cotización
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setCliente(cliente);

        // Inicializar la lista de ítems de cotización
        cotizacion.setItems(new ArrayList<>());

        // Guardar la cotización sin ítems
        cotizacion = cotizacionRepository.saveAndFlush(cotizacion);

        // Calcular el total y agregar los ítems de cotización
        BigDecimal total = BigDecimal.ZERO;

        for (CreateItemCotizacionDTO itemDTO : createCotizacionDTO.getItems()) {
            ItemCotizacionDTO nuevoItem = itemCotizacionService.addItemCotizacion(itemDTO, cotizacion.getId());
            total = total.add(nuevoItem.getSubtotal());
        }

        cotizacion.setTotal(total);

        // Guardar la cotización con los ítems
        cotizacion = cotizacionRepository.save(cotizacion);

        // Obtener el DTO de la cotización creada (incluyendo información del cliente)
        CotizacionDTO cotizacionDTO = cotizacionMapper.toDTO(cotizacion);

        // Asignar el subtotal calculado al DTO
        cotizacionDTO.setTotal(total);

        // Devolver el DTO de la cotización creada
        return cotizacionDTO;
    }
}