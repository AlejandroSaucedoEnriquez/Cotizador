package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateItemCotizacionDTO;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.bedu.Cotizador.model.Producto;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.bedu.Cotizador.repository.ItemCotizacionRepository;
import org.bedu.Cotizador.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private CotizacionMapper cotizacionMapper;

    public ItemCotizacion updateItemCotizacionCantidad(Long itemCotizacionId, int nuevaCantidad) {
        ItemCotizacion itemCotizacion = itemCotizacionRepository.findById(itemCotizacionId)
                .orElseThrow(() -> new EntityNotFoundException("ItemCotizacion no encontrado con ID: " + itemCotizacionId));

        itemCotizacion.setCantidad(nuevaCantidad);
        // Corregir la llamada al método calculateSubtotal agregando el precio del producto
        itemCotizacion.setSubtotal(calculateSubtotal(itemCotizacion, itemCotizacion.getProducto().getPrecio()));

        return itemCotizacionRepository.save(itemCotizacion);
    }


    private BigDecimal calculateSubtotal(ItemCotizacion itemCotizacion, BigDecimal precioProducto) {
        int cantidad = itemCotizacion.getCantidad();

        return precioProducto.multiply(BigDecimal.valueOf(cantidad));
    }
    public List<ItemCotizacion> saveAllItems(List<ItemCotizacion> itemCotizacionList) {
        return itemCotizacionRepository.saveAll(itemCotizacionList);
    }

    public List<ItemCotizacion> updateItems(UpdateItemCotizacionDTO updateItemCotizacionDTO, Cotizacion cotizacion) {
        List<CreateItemCotizacionDTO> itemsToAdd = updateItemCotizacionDTO.getItemsToAdd();
        List<Long> itemsToDeleteIds = updateItemCotizacionDTO.getItemsToDelete();

        List<ItemCotizacion> nuevosItems = createItems(itemsToAdd, cotizacion);
        deleteItems(itemsToDeleteIds);
        List<ItemCotizacion> itemsActualizados = combineItems(cotizacion.getItems(), nuevosItems);

        cotizacion.setItems(itemsActualizados);
        cotizacion.setSubtotal(calculateCotizacionSubtotal(itemsActualizados));

        cotizacionRepository.save(cotizacion);

        return itemsActualizados;
    }

    public List<ItemCotizacion> createItems(List<CreateItemCotizacionDTO> itemsToAdd, Cotizacion cotizacion) {
        return itemsToAdd.stream()
                .map(createItemCotizacionDTO -> {
                    ItemCotizacion item = cotizacionMapper.toModel(createItemCotizacionDTO);
                    item.setCotizacion(cotizacion);
                    return itemCotizacionRepository.save(item);
                })
                .collect(Collectors.toList());
    }

    private void deleteItems(List<Long> itemsToDeleteIds) {
        itemsToDeleteIds.forEach(id -> {
            itemCotizacionRepository.deleteById(id);
        });
    }

    private List<ItemCotizacion> combineItems(List<ItemCotizacion> existingItems, List<ItemCotizacion> newItems) {
        List<ItemCotizacion> combinedItems = new ArrayList<>(existingItems);
        combinedItems.addAll(newItems);
        return combinedItems;
    }

    private BigDecimal calculateCotizacionSubtotal(List<ItemCotizacion> items) {
        return items.stream()
                .map(item -> item.getSubtotal() != null ? item.getSubtotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}