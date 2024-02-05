package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import org.bedu.Cotizador.dto.itemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.createItemCotizacionDTO;
import org.bedu.Cotizador.model.cliente;
import org.bedu.Cotizador.model.cotizacion;
import org.bedu.Cotizador.model.itemCotizacion;
import org.bedu.Cotizador.model.producto;
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
class itemCotizacionServiceTest {

    @MockBean
    private org.bedu.Cotizador.repository.itemCotizacionRepository itemCotizacionRepository;

    @MockBean
    private org.bedu.Cotizador.repository.productoRepository productoRepository;

    @MockBean
    private org.bedu.Cotizador.repository.cotizacionRepository cotizacionRepository;

    @Autowired
    private itemCotizacionService service;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Test de error cuando no se inicializo la cotizacion")
    void addItemCotizacionCotizacionDoesNotExist() {
        createItemCotizacionDTO createDTO = new createItemCotizacionDTO();

        Optional<cotizacion> dummy = Optional.empty();

        when(cotizacionRepository.findById(anyLong())).thenReturn(dummy);

        assertThrows(EntityNotFoundException.class, () -> service.addItemCotizacion(createDTO, 20L));
    }

    @Test
    @DisplayName("Test de error cuando no existe el producto")
    void addItemCotizacionProductoDoesNotExist() {
        createItemCotizacionDTO createDTO = new createItemCotizacionDTO();

        cotizacion cotizacion = createEmptyCotizacion();

        Optional<producto> dummy = Optional.empty();
        when(productoRepository.findById(anyLong())).thenReturn(dummy);

        assertThrows(EntityNotFoundException.class, () -> service.addItemCotizacion(createDTO, cotizacion.getId()));
    }

    @Test
    @DisplayName("Test que crea un nuevo item por que no existe")
    void addItemCotizacionItemDoesNotPresent() {
        producto producto = new producto();

        producto.setId(1);
        producto.setNombre("Mancuerna Precor 5 kg");
        producto.setSku("ManNeg001");
        producto.setPrecio(new BigDecimal("500"));
        producto.setStock(25);
        producto.setDescripcion("Mancuerna hexagonal negro de cinco kg");
        producto.setCategoria("Accesorios");
        producto.setMarca("Precor");
        producto.setModelo("sg563");

        createItemCotizacionDTO createDTO = new createItemCotizacionDTO(producto.getId(), 5);

        cotizacion cotizacion = createEmptyCotizacion();

        itemCotizacion newItem = new itemCotizacion();

        newItem.setId(1);
        newItem.setCotizacion(cotizacion);
        newItem.setProducto(producto);
        newItem.setCantidad(createDTO.getCantidad());
        newItem.setPrecioUnitario(producto.getPrecio());
        newItem.setSubtotal((producto.getPrecio()).multiply(BigDecimal.valueOf(createDTO.getCantidad())));

        when(cotizacionRepository.findById(anyLong())).thenReturn(Optional.of(cotizacion));
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(itemCotizacionRepository.save(any(itemCotizacion.class))).thenReturn(newItem);

        itemCotizacionDTO dto = service.addItemCotizacion(createDTO, 1L);

        assertNotNull(dto);
        assertEquals(newItem.getId(), dto.getId());
        assertEquals(newItem.getProducto().getId(), dto.getProducto().getId());
        assertEquals(newItem.getCantidad(), dto.getCantidad());
        assertEquals(newItem.getPrecioUnitario(), dto.getPrecioUnitario());
        assertEquals(newItem.getSubtotal(), dto.getSubtotal());
    }

    @Test
    @DisplayName("Test actualiza un item que ya existe")
    void addItemCotizacionItemIsPresent() {
        cotizacion cotizacion = createCotizacionOneItem();

        createItemCotizacionDTO updateItem = new createItemCotizacionDTO(1L, 9);

        cotizacion.getItems().get(0).setCantidad(updateItem.getCantidad());
        cotizacion.getItems().get(0).setSubtotal(
                (cotizacion.getItems().get(0).getPrecioUnitario()).
                        multiply(BigDecimal.valueOf(updateItem.getCantidad()))
        );


        when(cotizacionRepository.findById(anyLong())).thenReturn(Optional.of(cotizacion));
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(cotizacion.getItems().get(0).getProducto()));
        when(itemCotizacionRepository.save(any(itemCotizacion.class))).thenReturn(cotizacion.getItems().get(0));

        itemCotizacionDTO dto = service.addItemCotizacion(updateItem, 1L);

        assertNotNull(dto);
        assertEquals(cotizacion.getItems().get(0).getId(), dto.getId());
        assertEquals(cotizacion.getItems().get(0).getProducto().getId(), dto.getProducto().getId());
        assertEquals(cotizacion.getItems().get(0).getCantidad(), dto.getCantidad());
        assertEquals(cotizacion.getItems().get(0).getPrecioUnitario(), dto.getPrecioUnitario());
        assertEquals(cotizacion.getItems().get(0).getSubtotal(), dto.getSubtotal());

    }

    private cotizacion createEmptyCotizacion() {
        cliente cliente = new cliente();
        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Avenida Vallarta #1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        cotizacion cotizacion = new cotizacion();

        ArrayList<itemCotizacion> itemList =new ArrayList<>();

        cotizacion.setId(1);
        cotizacion.setCliente(cliente);
        cotizacion.setItems(itemList);
        cotizacion.setFecha(LocalDate.now());
        cotizacion.setTotal(new BigDecimal(0));

        return cotizacion;
    }

    private cotizacion createCotizacionOneItem() {
        cotizacion cotizacion = createEmptyCotizacion();

        producto producto = new producto();

        producto.setId(1);
        producto.setNombre("Mancuerna Precor 5 kg");
        producto.setSku("ManNeg001");
        producto.setPrecio(new BigDecimal("500"));
        producto.setStock(25);
        producto.setDescripcion("Mancuerna hexagonal negro de cinco kg");
        producto.setCategoria("Accesorios");
        producto.setMarca("Precor");
        producto.setModelo("sg563");

        itemCotizacion newItem = new itemCotizacion();
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

