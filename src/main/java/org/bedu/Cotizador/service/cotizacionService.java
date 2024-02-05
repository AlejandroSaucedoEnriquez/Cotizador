package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.bedu.Cotizador.dto.cotizacionDTO;
import org.bedu.Cotizador.dto.itemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.createCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.createItemCotizacionDTO;
import org.bedu.Cotizador.mapper.cotizacionMapper;
import org.bedu.Cotizador.model.*;
import org.bedu.Cotizador.repository.clienteRepository;
import org.bedu.Cotizador.repository.cotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class cotizacionService {

    private final cotizacionRepository cotizacionRepository;


    private final clienteRepository clienteRepository;


    private final cotizacionMapper cotizacionMapper;

    private  final itemCotizacionService itemCotizacionService;

    @Autowired
    public cotizacionService(org.bedu.Cotizador.repository.cotizacionRepository cotizacionRepository, org.bedu.Cotizador.repository.clienteRepository clienteRepository, org.bedu.Cotizador.mapper.cotizacionMapper cotizacionMapper, org.bedu.Cotizador.service.itemCotizacionService itemCotizacionService) {
        this.cotizacionRepository = cotizacionRepository;
        this.clienteRepository = clienteRepository;
        this.cotizacionMapper = cotizacionMapper;
        this.itemCotizacionService = itemCotizacionService;
    }

    public cotizacionDTO crearCotizacion(createCotizacionDTO createCotizacionDTO) {
        cliente cliente = obtenerCliente(createCotizacionDTO.getClienteId());
        cotizacion cotizacion = crearCotizacionBase(cliente);
        BigDecimal total = calcularTotal(cotizacion, createCotizacionDTO.getItems());
        actualizarCotizacionConTotal(cotizacion, total);
        return mapearCotizacion(cotizacion);
    }

    private cliente obtenerCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("cliente no encontrado"));
    }

    public cotizacion crearCotizacionBase(cliente cliente) {
        cotizacion cotizacion = new cotizacion();
        cotizacion.setCliente(cliente);
        cotizacion.setItems(new ArrayList<>());
        return cotizacionRepository.saveAndFlush(cotizacion);
    }

    private BigDecimal calcularTotal(cotizacion cotizacion, List<createItemCotizacionDTO> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (createItemCotizacionDTO itemDTO : items) {
            itemCotizacionDTO nuevoItem = itemCotizacionService.addItemCotizacion(itemDTO, cotizacion.getId());
            total = total.add(nuevoItem.getSubtotal());
        }
        return total;
    }

    private void actualizarCotizacionConTotal(cotizacion cotizacion, BigDecimal total) {
        cotizacion.setTotal(total);
        cotizacionRepository.save(cotizacion);
    }

    private cotizacionDTO mapearCotizacion(cotizacion cotizacion) {
        cotizacionDTO cotizacionDTO = cotizacionMapper.toDTO(cotizacion);
        cotizacionDTO.setTotal(cotizacion.getTotal());
        return cotizacionDTO;
    }
}