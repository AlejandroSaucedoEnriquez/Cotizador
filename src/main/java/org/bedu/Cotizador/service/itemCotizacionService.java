package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.bedu.Cotizador.dto.itemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.createItemCotizacionDTO;
import org.bedu.Cotizador.model.cotizacion;
import org.bedu.Cotizador.model.itemCotizacion;
import org.bedu.Cotizador.model.producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@Transactional
public class itemCotizacionService {


    private final org.bedu.Cotizador.repository.itemCotizacionRepository itemCotizacionRepository;


    private final org.bedu.Cotizador.repository.productoRepository productoRepository;


    private final org.bedu.Cotizador.repository.cotizacionRepository cotizacionRepository;

    private final org.bedu.Cotizador.mapper.itemCotizacionMapper itemCotizacionMapper;

    @Autowired
    public itemCotizacionService(org.bedu.Cotizador.repository.itemCotizacionRepository itemCotizacionRepository, org.bedu.Cotizador.repository.productoRepository productoRepository, org.bedu.Cotizador.repository.cotizacionRepository cotizacionRepository, org.bedu.Cotizador.mapper.itemCotizacionMapper itemCotizacionMapper) {
        this.itemCotizacionRepository = itemCotizacionRepository;
        this.productoRepository = productoRepository;
        this.cotizacionRepository = cotizacionRepository;
        this.itemCotizacionMapper = itemCotizacionMapper;
    }

    public itemCotizacionDTO addItemCotizacion(createItemCotizacionDTO createItemCotizacionDTO, Long cotizacionId) {
        cotizacion cotizacion = getCotizacionById(cotizacionId);
        producto producto = getProductoById(createItemCotizacionDTO.getProductoId());

        Optional<itemCotizacion> existingItem = findExistingItem(cotizacion, producto);

        if (existingItem.isPresent()) {
            return updateExistingItem(existingItem.get(), createItemCotizacionDTO);
        } else {
            return createNewItem(cotizacion, producto, createItemCotizacionDTO);
        }
    }

    public cotizacion getCotizacionById(Long cotizacionId) {
        return cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new EntityNotFoundException("cotizacion no encontrada"));
    }

    private producto getProductoById(Long productoId) {
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new EntityNotFoundException("producto no encontrado"));
    }

    private Optional<itemCotizacion> findExistingItem(cotizacion cotizacion, producto producto) {
        return cotizacion.getItems().stream()
                .filter(item -> item.getProducto().equals(producto))
                .findFirst();
    }

    private itemCotizacionDTO updateExistingItem(itemCotizacion existingItem, createItemCotizacionDTO createItemCotizacionDTO) {
        existingItem.setCantidad(createItemCotizacionDTO.getCantidad());
        existingItem.setSubtotal(existingItem.getPrecioUnitario().multiply(BigDecimal.valueOf(existingItem.getCantidad())));
        return itemCotizacionMapper.toDTO(itemCotizacionRepository.save(existingItem));
    }

    private itemCotizacionDTO createNewItem(cotizacion cotizacion, producto producto, createItemCotizacionDTO createItemCotizacionDTO) {
        itemCotizacion newItem = new itemCotizacion();
        newItem.setCotizacion(cotizacion);
        newItem.setProducto(producto);
        newItem.setCantidad(createItemCotizacionDTO.getCantidad());

        BigDecimal precioUnitario = producto.getPrecio();
        BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(createItemCotizacionDTO.getCantidad()));
        newItem.setPrecioUnitario(precioUnitario);
        newItem.setSubtotal(subtotal);

        newItem = itemCotizacionRepository.save(newItem);

        cotizacion.getItems().add(newItem);
        cotizacionRepository.save(cotizacion);

        return itemCotizacionMapper.toDTO(newItem);
    }
}