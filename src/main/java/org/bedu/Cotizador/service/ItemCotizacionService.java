package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.mapper.ItemCotizacionMapper;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.bedu.Cotizador.model.Producto;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.bedu.Cotizador.repository.ItemCotizacionRepository;
import org.bedu.Cotizador.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@Transactional
public class ItemCotizacionService {

    @Autowired
    private ItemCotizacionRepository itemCotizacionRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private ItemCotizacionMapper itemCotizacionMapper;

    public ItemCotizacionDTO addItemCotizacion(CreateItemCotizacionDTO createItemCotizacionDTO, Long cotizacionId) {
        Cotizacion cotizacion = getCotizacionById(cotizacionId);
        Producto producto = getProductoById(createItemCotizacionDTO.getProductoId());

        Optional<ItemCotizacion> existingItem = findExistingItem(cotizacion, producto);

        if (existingItem.isPresent()) {
            return updateExistingItem(existingItem.get(), createItemCotizacionDTO);
        } else {
            return createNewItem(cotizacion, producto, createItemCotizacionDTO);
        }
    }

    private Cotizacion getCotizacionById(Long cotizacionId) {
        return cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new EntityNotFoundException("Cotizacion no encontrada"));
    }

    private Producto getProductoById(Long productoId) {
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    private Optional<ItemCotizacion> findExistingItem(Cotizacion cotizacion, Producto producto) {
        return cotizacion.getItems().stream()
                .filter(item -> item.getProducto().equals(producto))
                .findFirst();
    }

    private ItemCotizacionDTO updateExistingItem(ItemCotizacion existingItem, CreateItemCotizacionDTO createItemCotizacionDTO) {
        existingItem.setCantidad(existingItem.getCantidad() + createItemCotizacionDTO.getCantidad());
        existingItem.setSubtotal(existingItem.getPrecioUnitario().multiply(BigDecimal.valueOf(existingItem.getCantidad())));
        return itemCotizacionMapper.toDTO(itemCotizacionRepository.save(existingItem));
    }

    public ItemCotizacionDTO createNewItem(Cotizacion cotizacion, Producto producto, CreateItemCotizacionDTO createItemCotizacionDTO) {
        ItemCotizacion newItem = new ItemCotizacion();
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