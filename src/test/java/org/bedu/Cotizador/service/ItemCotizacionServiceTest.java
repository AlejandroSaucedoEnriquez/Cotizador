package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.bedu.Cotizador.model.Producto;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.bedu.Cotizador.repository.ItemCotizacionRepository;
import org.bedu.Cotizador.repository.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ItemCotizacionServiceTest {

    @MockBean
    private ItemCotizacionRepository itemCotizacionRepository;

    @MockBean
    private ProductoRepository productoRepository;

    @MockBean
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private ItemCotizacionService service;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Test de error cuando no se inicializo la Cotizacion")
    public void addItemCotizacionCotizacionDoesNotExist() {
        CreateItemCotizacionDTO createDTO = new CreateItemCotizacionDTO();

        Optional<Cotizacion> dummy = Optional.empty();

        when(cotizacionRepository.findById(anyLong())).thenReturn(dummy);

        assertThrows(EntityNotFoundException.class, () -> service.addItemCotizacion(createDTO, 20L));
    }

    @Test
    @DisplayName("Test de error cuando no existe el producto")
    public void addItemCotizacionProductoDoesNotExist() {
        CreateItemCotizacionDTO createDTO = new CreateItemCotizacionDTO();

        Cotizacion cotizacion = createEmptyCotizacion();

        Optional<Producto> dummy = Optional.empty();
        when(productoRepository.findById(anyLong())).thenReturn(dummy);

        assertThrows(EntityNotFoundException.class, () -> service.addItemCotizacion(createDTO, cotizacion.getId()));
    }

    @Test
    @DisplayName("Test que crea un nuevo item por que no existe")
    public void addItemCotizacionItemDoesNotPresent() {
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

        CreateItemCotizacionDTO createDTO = new CreateItemCotizacionDTO(producto.getId(), 5);

        Cotizacion cotizacion = createEmptyCotizacion();

        ItemCotizacion newItem = new ItemCotizacion();

        newItem.setId(1);
        newItem.setCotizacion(cotizacion);
        newItem.setProducto(producto);
        newItem.setCantidad(createDTO.getCantidad());
        newItem.setPrecioUnitario(producto.getPrecio());
        newItem.setSubtotal((producto.getPrecio()).multiply(BigDecimal.valueOf(createDTO.getCantidad())));

        when(cotizacionRepository.findById(anyLong())).thenReturn(Optional.of(cotizacion));
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(itemCotizacionRepository.save(any(ItemCotizacion.class))).thenReturn(newItem);

        ItemCotizacionDTO dto = service.addItemCotizacion(createDTO, 1L);

        assertNotNull(dto);
        assertEquals(newItem.getId(), dto.getId());
        assertEquals(newItem.getProducto().getId(), dto.getProducto().getId());
        assertEquals(newItem.getCantidad(), dto.getCantidad());
        assertEquals(newItem.getPrecioUnitario(), dto.getPrecioUnitario());
        assertEquals(newItem.getSubtotal(), dto.getSubtotal());
    }

    @Test
    @DisplayName("Test actualiza un item que ya existe")
    public void addItemCotizacionItemIsPresent() {
        Cotizacion cotizacion = createCotizacionOneItem();

        CreateItemCotizacionDTO updateItem = new CreateItemCotizacionDTO(1L, 9);

        cotizacion.getItems().get(0).setCantidad(updateItem.getCantidad());
        cotizacion.getItems().get(0).setSubtotal(
                (cotizacion.getItems().get(0).getPrecioUnitario()).
                        multiply(BigDecimal.valueOf(updateItem.getCantidad()))
        );


        when(cotizacionRepository.findById(anyLong())).thenReturn(Optional.of(cotizacion));
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(cotizacion.getItems().get(0).getProducto()));
        when(itemCotizacionRepository.save(any(ItemCotizacion.class))).thenReturn(cotizacion.getItems().get(0));

        ItemCotizacionDTO dto = service.addItemCotizacion(updateItem, 1L);

        assertNotNull(dto);
        assertEquals(cotizacion.getItems().get(0).getId(), dto.getId());
        assertEquals(cotizacion.getItems().get(0).getProducto().getId(), dto.getProducto().getId());
        assertEquals(cotizacion.getItems().get(0).getCantidad(), dto.getCantidad());
        assertEquals(cotizacion.getItems().get(0).getPrecioUnitario(), dto.getPrecioUnitario());
        assertEquals(cotizacion.getItems().get(0).getSubtotal(), dto.getSubtotal());

    }

    public Cotizacion createEmptyCotizacion() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Av. Vallarta 1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        Cotizacion cotizacion = new Cotizacion();

        ArrayList<ItemCotizacion> itemList =new ArrayList<>();

        cotizacion.setId(1);
        cotizacion.setCliente(cliente);
        cotizacion.setItems(itemList);
        cotizacion.setFecha(LocalDate.now());
        cotizacion.setTotal(new BigDecimal(0));

        return cotizacion;
    }

    public Cotizacion createCotizacionOneItem() {
        Cotizacion cotizacion = createEmptyCotizacion();

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

        ItemCotizacion newItem = new ItemCotizacion();
        newItem.setId(1);
        newItem.setCotizacion(cotizacion);
        newItem.setProducto(producto);
        newItem.setCantidad(3);
        newItem.setPrecioUnitario(producto.getPrecio());
        newItem.setSubtotal((producto.getPrecio()).multiply(BigDecimal.valueOf(newItem.getCantidad())));

        cotizacion.getItems().add(newItem);
        cotizacionRepository.save(cotizacion);
        return cotizacion;
    }
}

