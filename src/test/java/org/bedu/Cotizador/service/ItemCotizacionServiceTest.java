package org.bedu.Cotizador.service;


import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.mapper.ItemCotizacionMapper;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.bedu.Cotizador.model.Producto;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.bedu.Cotizador.repository.ItemCotizacionRepository;
import org.bedu.Cotizador.repository.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class ItemCotizacionServiceTest {

    @MockBean
    private ItemCotizacionRepository itemCotizacionRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private ItemCotizacionService service;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Test para crear un nuevo ItemCotizacion")
    public void createNewItemTest() {
        Producto producto = new Producto();

        producto.setId(1);
        producto.setNombre("Mancuerna 5 kg");
        producto.setSku("28394");
        producto.setPrecio(new BigDecimal("500"));
        producto.setStock(25);
        producto.setDescripcion("Mancuerna hexagoanl de 5 kg.");
        producto.setCategoria("Peso libre.");
        producto.setMarca("Tryman");
        producto.setModelo("sg563");

        Cotizacion cotizacion = createEmptyCotizacion();

        CreateItemCotizacionDTO createItem = new CreateItemCotizacionDTO(producto.getId(), 2);

        ItemCotizacion newItem = new ItemCotizacion();

        newItem.setId(1);
        newItem.setCotizacion(cotizacion);
        newItem.setProducto(producto);
        newItem.setCantidad(createItem.getCantidad());
        newItem.setPrecioUnitario(producto.getPrecio());
        newItem.setSubtotal((producto.getPrecio()).multiply(BigDecimal.valueOf(createItem.getCantidad())));

        when(itemCotizacionRepository.save(any(ItemCotizacion.class))).thenReturn(newItem);

        ItemCotizacionDTO dto = service.createNewItem(cotizacion, producto, createItem);

        assertNotNull(dto);
        assertEquals(newItem.getId(), dto.getId());

    }

    public Cotizacion createEmptyCotizacion() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Av. Vallarta 1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        List<ItemCotizacion> items = new ArrayList<>();

        Cotizacion cotizacion = new Cotizacion();

        cotizacion.setId(1);
        cotizacion.setCliente(cliente);
        cotizacion.setItems(items);

        return cotizacion;
    }
}

