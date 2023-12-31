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
        // Recuperar la cotización
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new EntityNotFoundException("Cotizacion no encontrada"));

        // Recuperar el producto
        Producto producto = productoRepository.findById(createItemCotizacionDTO.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        // Crear el ItemCotizacion
        ItemCotizacion itemCotizacion = new ItemCotizacion();
        itemCotizacion.setCotizacion(cotizacion);
        itemCotizacion.setProducto(producto);
        itemCotizacion.setCantidad(createItemCotizacionDTO.getCantidad());

        // Calcular el subtotal
        BigDecimal precioUnitario = producto.getPrecio();
        BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(createItemCotizacionDTO.getCantidad()));
        itemCotizacion.setPrecioUnitario(precioUnitario);
        itemCotizacion.setSubtotal(subtotal);

        // Guardar el ItemCotizacion
        itemCotizacion = itemCotizacionRepository.save(itemCotizacion);

        // Actualizar la cotización con el nuevo ItemCotizacion
        cotizacion.getItems().add(itemCotizacion);
        cotizacionRepository.save(cotizacion);

        // Devolver el DTO del ItemCotizacion creado
        return itemCotizacionMapper.toDTO(itemCotizacion);
    }
}