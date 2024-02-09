package org.bedu.Cotizador.service;

import jakarta.transaction.Transactional;

import org.bedu.Cotizador.componet.CotizacionComponent;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.mapper.ItemCotizacionMapper;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.bedu.Cotizador.model.Producto;
import org.bedu.Cotizador.repository.ItemCotizacionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@Transactional
public class ItemCotizacionService {


    private final ItemCotizacionRepository itemCotizacionRepository;

    private final CotizacionComponent component;

    private final ItemCotizacionMapper itemCotizacionMapper;

    public ItemCotizacionService(ItemCotizacionRepository itemCotizacionRepository, CotizacionComponent component, ItemCotizacionMapper itemCotizacionMapper) {
        this.itemCotizacionRepository = itemCotizacionRepository;
        this.component = component;
        this.itemCotizacionMapper = itemCotizacionMapper;
    }


    public ItemCotizacionDTO addItemCotizacion(CreateItemCotizacionDTO createItemCotizacionDTO, Long cotizacionId) {
        Cotizacion cotizacion = component.getCotizacion(cotizacionId);
        ItemCotizacion itemCotizacion = itemCotizacionMapper.toModel(createItemCotizacionDTO);

        Optional<ItemCotizacion> existingItem = findExistingItem(cotizacion, itemCotizacion);

        if (existingItem.isPresent()) {
            return updateExistingItem(existingItem.get(), createItemCotizacionDTO);
        } else {
            return createNewItem(cotizacion, itemCotizacion.getProducto(), createItemCotizacionDTO);
        }
    }

    private Optional<ItemCotizacion> findExistingItem(Cotizacion cotizacion, ItemCotizacion itemCotizacion) {
        return cotizacion.getItems().stream()
                .filter(item -> item.getProducto().equals(itemCotizacion.getProducto()))
                .findFirst();
    }

    private ItemCotizacionDTO updateExistingItem(ItemCotizacion existingItem, CreateItemCotizacionDTO createItemCotizacionDTO) {
        existingItem.setCantidad(createItemCotizacionDTO.getCantidad());
        existingItem.setSubtotal(existingItem.getPrecioUnitario().multiply(BigDecimal.valueOf(existingItem.getCantidad())));
        return itemCotizacionMapper.toDTO(itemCotizacionRepository.save(existingItem));
    }

    private ItemCotizacionDTO createNewItem(Cotizacion cotizacion, Producto producto, CreateItemCotizacionDTO createItemCotizacionDTO) {
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
        component.saveCotizacion(cotizacion);

        return itemCotizacionMapper.toDTO(newItem);
    }
}