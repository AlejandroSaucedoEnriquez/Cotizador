package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.*;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CotizacionMapper cotizacionMapper;

    @Autowired
    private ItemCotizacionService itemCotizacionService;

    public CotizacionDTO crearCotizacion(CreateCotizacionDTO createCotizacionDTO) {
        Cliente cliente = obtenerCliente(createCotizacionDTO.getClienteId());
        Cotizacion cotizacion = crearCotizacionBase(cliente);
        BigDecimal total = calcularTotal(cotizacion, createCotizacionDTO.getItems());
        actualizarCotizacionConTotal(cotizacion, total);
        return mapearCotizacion(cotizacion);
    }

    private Cliente obtenerCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }

    private Cotizacion crearCotizacionBase(Cliente cliente) {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setCliente(cliente);
        cotizacion.setItems(new ArrayList<>());
        return cotizacionRepository.saveAndFlush(cotizacion);
    }

    private BigDecimal calcularTotal(Cotizacion cotizacion, List<CreateItemCotizacionDTO> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (CreateItemCotizacionDTO itemDTO : items) {
            ItemCotizacionDTO nuevoItem = itemCotizacionService.addItemCotizacion(itemDTO, cotizacion.getId());
            total = total.add(nuevoItem.getSubtotal());
        }
        return total;
    }

    private void actualizarCotizacionConTotal(Cotizacion cotizacion, BigDecimal total) {
        cotizacion.setTotal(total);
        cotizacionRepository.save(cotizacion);
    }

    private CotizacionDTO mapearCotizacion(Cotizacion cotizacion) {
        CotizacionDTO cotizacionDTO = cotizacionMapper.toDTO(cotizacion);
        cotizacionDTO.setTotal(cotizacion.getTotal());
        return cotizacionDTO;
    }
}