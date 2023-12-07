package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.model.Factura;
import org.bedu.Cotizador.dto.FacturaDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateCotizacionDTO;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.bedu.Cotizador.model.Producto;
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
public class CotizacionService {
    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private CotizacionMapper cotizacionMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    //Listar las cotizaciones ehcas
    public List<CotizacionDTO> findAll() {
        List<Cotizacion> cotizaciones = cotizacionRepository.findAll();
        return cotizaciones.stream()
                .map(cotizacionMapper::toDTO)
                .collect(Collectors.toList());
    }
    //Guardar
    public CotizacionDTO save(CreateCotizacionDTO data) {
        // Validar que el cliente existe
        Cliente cliente = clienteRepository.findById(data.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Cotizacion cotizacion = cotizacionMapper.toModel(data);
        cotizacion.setCliente(cliente);

        // Calcular subtotal
        BigDecimal subtotal = calcularSubtotal(cotizacion);
        cotizacion.setSubtotal(subtotal);

        Cotizacion savedCotizacion = cotizacionRepository.save(cotizacion);
        return cotizacionMapper.toDTO(savedCotizacion);
    }

    //Obtener Cotizacion por ID de la Cotizacion
    public CotizacionDTO getCotizacionById(Long cotizacionId) {
        Optional<Cotizacion> cotizacion = cotizacionRepository.findById(cotizacionId);
        if (cotizacion.isPresent()) {
            return cotizacionMapper.toDTO(cotizacion.get());
        } else {
            throw new RuntimeException("Cotización no encontrada");
        }
    }

    //Eliminar
    public void deleteCotizacion(Long cotizacionId) {
        cotizacionRepository.deleteById(cotizacionId);
    }

    //Genera la factura de la cotizacion
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

    //Actualiza la cotizacion
    public CotizacionDTO update(Long cotizacionId, UpdateCotizacionDTO data) {

        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        // Agregar nuevos productos
        if (data.getItemsToAdd() != null) {
            for (CreateItemCotizacionDTO newItem : data.getItemsToAdd()) {
                Producto producto = productoRepository.findById(newItem.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                ItemCotizacion newItemCotizacion = cotizacionMapper.toModel(newItem);
                newItemCotizacion.setProducto(producto);
                newItemCotizacion.setCotizacion(cotizacion);

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

    //Calcula el subtotal segun el precio y cantidad de los Productos
    private BigDecimal calcularSubtotal(Cotizacion cotizacion) {
        return cotizacion.getItems().stream()
                .map(item -> item.getSubtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
