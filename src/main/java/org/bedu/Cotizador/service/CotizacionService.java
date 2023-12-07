package org.bedu.Cotizador.service;

import lombok.extern.slf4j.Slf4j;
import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.FacturaDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateCotizacionDTO;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.*;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.bedu.Cotizador.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private CotizacionMapper cotizacionMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Listar las cotizaciones hechas
    public List<CotizacionDTO> findAll() {
        List<Cotizacion> cotizaciones = cotizacionRepository.findAll();
        return cotizaciones.stream()
                .map(cotizacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Guardar
    public CotizacionDTO save(CreateCotizacionDTO data) {
        // Validar que el cliente existe
        Cliente cliente = clienteRepository.findById(data.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Crear nueva cotización
        Cotizacion cotizacion = cotizacionMapper.toModel(data);
        cotizacion.setCliente(cliente);

        // Configurar los ítems de la cotización
        if (data.getItems() != null) {
            for (CreateItemCotizacionDTO itemDTO : data.getItems()) {
                // Validar que el producto existe
                Producto producto = productoRepository.findById(itemDTO.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                // Crear nuevo ítem de cotización
                ItemCotizacion newItem = new ItemCotizacion();
                newItem.setProducto(producto);
                newItem.setCotizacion(cotizacion);
                newItem.setCantidad(itemDTO.getCantidad());

                // Validar que el precio unitario no sea nulo o negativo
                if (itemDTO.getPrecioUnitario() == null || itemDTO.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
                    log.error("Precio unitario no válido. Producto: {}, Precio Unitario: {}", producto.getNombre(), itemDTO.getPrecioUnitario());
                    throw new RuntimeException("Precio unitario no válido");
                }

                newItem.setPrecioUnitario(itemDTO.getPrecioUnitario());

                // Calcular el subtotal para este ítem
                BigDecimal subtotal = newItem.getPrecioUnitario().multiply(new BigDecimal(newItem.getCantidad()));
                newItem.setSubtotal(subtotal);

                // Agregar el ítem a la lista de ítems de la cotización
                cotizacion.getItems().add(newItem);
            }
        }

        // Calcular subtotal general de la cotización
        BigDecimal subtotalGeneral = calcularSubtotal(cotizacion);
        cotizacion.setSubtotal(subtotalGeneral);

        Cotizacion savedCotizacion = cotizacionRepository.save(cotizacion);
        return cotizacionMapper.toDTO(savedCotizacion);
    }

    // Obtener Cotizacion por ID de la Cotizacion
    public CotizacionDTO getCotizacionById(Long cotizacionId) {
        Optional<Cotizacion> cotizacion = cotizacionRepository.findById(cotizacionId);
        if (cotizacion.isPresent()) {
            return cotizacionMapper.toDTO(cotizacion.get());
        } else {
            throw new RuntimeException("Cotización no encontrada");
        }
    }

    // Eliminar
    public void deleteCotizacion(Long cotizacionId) {
        cotizacionRepository.deleteById(cotizacionId);
    }

    // Genera la factura de la cotizacion
    public FacturaDTO generarFactura(Long cotizacionId) {
        Optional<Cotizacion> cotizacion = cotizacionRepository.findById(cotizacionId);
        if (cotizacion.isPresent()) {
            // Lógica para generar la factura
            Factura factura = new Factura();
            return factura.toDTO();
        } else {
            throw new RuntimeException("Cotización no encontrada");
        }
    }

    // Actualiza la cotizacion
    public CotizacionDTO update(Long cotizacionId, UpdateCotizacionDTO data) {

        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        // Agregar nuevos productos
        if (data.getItemsToAdd() != null) {
            for (CreateItemCotizacionDTO newItem : data.getItemsToAdd()) {
                Producto producto = productoRepository.findById(newItem.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                ItemCotizacion newItemCotizacion = new ItemCotizacion();
                newItemCotizacion.setProducto(producto);
                newItemCotizacion.setCotizacion(cotizacion);
                newItemCotizacion.setCantidad(newItem.getCantidad());

                if (newItem.getPrecioUnitario() == null || newItem.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
                    throw new RuntimeException("Precio unitario no válido");
                }

                newItemCotizacion.setPrecioUnitario(newItem.getPrecioUnitario());

                BigDecimal subtotal = newItemCotizacion.getPrecioUnitario().multiply(new BigDecimal(newItemCotizacion.getCantidad()));
                newItemCotizacion.setSubtotal(subtotal);

                cotizacion.getItems().add(newItemCotizacion);
            }
        }

        // Eliminar productos existentes
        if (data.getItemsToDelete() != null) {
            for (Long itemId : data.getItemsToDelete()) {
                cotizacion.getItems().removeIf(item -> item.getId().equals(itemId));
            }
        }

        // Calcular el nuevo subtotal
        BigDecimal subtotal = calcularSubtotal(cotizacion);
        cotizacion.setSubtotal(subtotal);

        Cotizacion updatedCotizacion = cotizacionRepository.save(cotizacion);
        return cotizacionMapper.toDTO(updatedCotizacion);
    }

    // Calcula el subtotal según el precio y cantidad de los Productos
    private BigDecimal calcularSubtotal(Cotizacion cotizacion) {
        return cotizacion.getItems().stream()
                .filter(item -> item.getSubtotal() != null)
                .map(ItemCotizacion::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}