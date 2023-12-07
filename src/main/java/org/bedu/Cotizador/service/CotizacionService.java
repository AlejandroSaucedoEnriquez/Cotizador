package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.FacturaDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateItemCotizacionDTO;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.*;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.bedu.Cotizador.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public CotizacionDTO guardarCotizacionParaCliente(CreateCotizacionDTO createCotizacionDTO) {
        Cliente cliente = clienteRepository.findById(createCotizacionDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + createCotizacionDTO.getClienteId()));

        Cotizacion cotizacion = cotizacionMapper.toCotizacion(createCotizacionDTO);
        cotizacion.setCliente(cliente);

        // Guardar la cotización primero para obtener un ID asignado
        Cotizacion savedCotizacion = cotizacionRepository.save(cotizacion);

        // Crear y asociar los ItemCotizacion después de guardar la Cotizacion
        List<CreateItemCotizacionDTO> itemsToAdd = createCotizacionDTO.getItems();
        List<Long> itemsToDeleteIds = Collections.emptyList();  // No hay elementos para eliminar al principio

        List<ItemCotizacion> nuevosItems = itemCotizacionService.createItems(itemsToAdd, savedCotizacion);
        itemCotizacionService.updateItems(new UpdateItemCotizacionDTO(itemsToAdd, itemsToDeleteIds), savedCotizacion);

        // Calcular el subtotal
        savedCotizacion.setSubtotal(calculateCotizacionSubtotal(savedCotizacion.getItems()));

        // Guardar la cotización y sus ítems nuevamente después de la asociación
        cotizacionRepository.save(savedCotizacion);

        return cotizacionMapper.toDTO(savedCotizacion);
    }


    public void completarCotizacion(Long cotizacionId) {
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new EntityNotFoundException("Cotización no encontrada con ID: " + cotizacionId));

        cotizacionRepository.save(cotizacion);
    }

    public CotizacionDTO obtenerCotizacion(Long cotizacionId) {
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new EntityNotFoundException("Cotización no encontrada con ID: " + cotizacionId));

        return cotizacionMapper.toDTO(cotizacion);
    }

    public void eliminarCotizacion(Long cotizacionId) {
        cotizacionRepository.deleteById(cotizacionId);
    }

    private BigDecimal calculateCotizacionSubtotal(List<ItemCotizacion> items) {
        return items.stream()
                .map(item -> (item.getSubtotal() != null) ? item.getSubtotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
