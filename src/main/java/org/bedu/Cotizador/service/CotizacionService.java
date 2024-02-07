package org.bedu.Cotizador.service;

import jakarta.transaction.Transactional;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.*;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class CotizacionService {

    private final CotizacionRepository cotizacionRepository;

    private final CotizacionMapper cotizacionMapper;

    private  final ItemCotizacionService itemCotizacionService;

    @Autowired
    public CotizacionService(CotizacionRepository cotizacionRepository, CotizacionMapper cotizacionMapper, ItemCotizacionService itemCotizacionService) {
        this.cotizacionRepository = cotizacionRepository;
        this.cotizacionMapper = cotizacionMapper;
        this.itemCotizacionService = itemCotizacionService;
    }

    public CotizacionDTO crearCotizacion(CreateCotizacionDTO createCotizacionDTO) {
        Cotizacion cotizacion = crearCotizacionBase(createCotizacionDTO);
        BigDecimal total = calcularTotal(cotizacion, createCotizacionDTO.getItems());
        actualizarCotizacionConTotal(cotizacion, total);
        return cotizacionMapper.toDTO(cotizacion);
    }

    public Cotizacion crearCotizacionBase(CreateCotizacionDTO createItemCotizacionDTO) {
        Cotizacion cotizacion = cotizacionMapper.toModel(createItemCotizacionDTO);
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
}
