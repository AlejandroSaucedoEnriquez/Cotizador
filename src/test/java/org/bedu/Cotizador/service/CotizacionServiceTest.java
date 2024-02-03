package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.bedu.Cotizador.model.Producto;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.bedu.Cotizador.repository.ItemCotizacionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CotizacionServiceTest {

    @MockBean
    private CotizacionRepository repository;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ItemCotizacionRepository itemCotizacionRepository;

    @Autowired
    private CotizacionService service;

    @InjectMocks
    private ItemCotizacionService itemCotizacionService;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Test de error cuando no encuentra el Cliente")
    void crearCotizacionDoesNotCliente(){
        CreateCotizacionDTO createDTO = new CreateCotizacionDTO();

        Optional<Cliente> dummy = Optional.empty();

        when(clienteRepository.findById(anyLong())).thenReturn(dummy);

        assertThrows(EntityNotFoundException.class, ()-> service.crearCotizacion(createDTO));
    }

    @Test
    @DisplayName("Test crea una cotizacion con dos productos")
    void crearCotizacionTwoProducts() {
        long id = 1;
        Cliente cliente = createCliente(id);
        Producto producto1 = createFirstProducto();
        Producto producto2 = createSecondProducto();
        List<CreateItemCotizacionDTO> createItemList = new ArrayList<>();
        CreateItemCotizacionDTO createItemCotizacionDTO1 = new CreateItemCotizacionDTO(producto1.getId(), 4);
        CreateItemCotizacionDTO createItemCotizacionDTO2 = new CreateItemCotizacionDTO(producto2.getId(), 2);
        createItemList.add(createItemCotizacionDTO1);
        createItemList.add(createItemCotizacionDTO2);

        ItemCotizacionDTO itemCotizacionDTO1 = createFirstItemDTO(productoToDTO(producto1), createItemCotizacionDTO1);
        ItemCotizacionDTO itemCotizacionDTO2 = createSecondItemDTO(productoToDTO(producto2), createItemCotizacionDTO2);

        CreateCotizacionDTO createCotizacionDTO = new CreateCotizacionDTO(cliente.getId(), createItemList);

        List<ItemCotizacion> itemCotizacions = new ArrayList<>();

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(1L);
        cotizacion.setCliente(cliente);

        ItemCotizacion itemCotizacion1 = DtoToEntity(itemCotizacionDTO1, cotizacion, producto1);
        ItemCotizacion itemCotizacion2 = DtoToEntity(itemCotizacionDTO2, cotizacion, producto2);
        itemCotizacions.add(itemCotizacion1);
        itemCotizacions.add(itemCotizacion2);

        BigDecimal total = BigDecimal.ZERO;
        for (ItemCotizacion item : itemCotizacions) {
            total = total.add(item.getSubtotal());
        }

        cotizacion.setItems(itemCotizacions);
        cotizacion.setFecha(LocalDate.now());
        cotizacion.setTotal(total);

        System.out.println("Imprimir cotizacion : "+cotizacion);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(repository.save(any(Cotizacion.class))).thenReturn(cotizacion);

        CotizacionDTO result = service.crearCotizacion(createCotizacionDTO);

        assertNotNull(result);
    }

    private Cliente createCliente(long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Av. Vallarta 1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        return cliente;
    }

    private Producto createFirstProducto() {
        Producto producto = new Producto();

        producto.setId(1L);
        producto.setNombre("Mancuerna 5 kg");
        producto.setSku("28394");
        producto.setPrecio(new BigDecimal("500"));
        producto.setStock(25);
        producto.setDescripcion("Mancuerna hexagoanl de 5 kg.");
        producto.setCategoria("Peso libre.");
        producto.setMarca("Tryman");
        producto.setModelo("sg563");

        return producto;
    }

    private Producto createSecondProducto() {
        Producto producto = new Producto();

        producto.setId(2L);
        producto.setNombre("Mancuerna 10 kg");
        producto.setSku("4233");
        producto.setPrecio(new BigDecimal("650"));
        producto.setStock(25);
        producto.setDescripcion("Mancuerna hexagoanl de 10 kg.");
        producto.setCategoria("Peso libre.");
        producto.setMarca("Tryman");
        producto.setModelo("sg573");

        return producto;
    }

    private ProductoDTO productoToDTO (Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setId((int) producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setSku(productoDTO.getSku());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(producto.getStock());
        productoDTO.setDescripcion(productoDTO.getDescripcion());
        productoDTO.setCategoria(productoDTO.getCategoria());
        productoDTO.setMarca(productoDTO.getMarca());
        productoDTO.setModelo(productoDTO.getModelo());

        return productoDTO;
    }

    private ItemCotizacionDTO createFirstItemDTO(ProductoDTO producto, CreateItemCotizacionDTO createItemCotizacionDTO) {
        ItemCotizacionDTO itemCotizacionDTO = new ItemCotizacionDTO();
        itemCotizacionDTO.setId(1L);
        itemCotizacionDTO.setProducto(producto);
        itemCotizacionDTO.setCantidad(createItemCotizacionDTO.getCantidad());
        itemCotizacionDTO.setPrecioUnitario(producto.getPrecio());
        itemCotizacionDTO.setSubtotal((itemCotizacionDTO.getPrecioUnitario()).multiply(BigDecimal.valueOf(itemCotizacionDTO.getCantidad())));

        return itemCotizacionDTO;
    }

    private ItemCotizacion DtoToEntity (ItemCotizacionDTO dto, Cotizacion cotizacion, Producto producto) {
        ItemCotizacion itemCotizacion = new ItemCotizacion();
        itemCotizacion.setId(dto.getId());
        itemCotizacion.setCotizacion(cotizacion);
        itemCotizacion.setProducto(producto);
        itemCotizacion.setCantidad(dto.getCantidad());
        itemCotizacion.setPrecioUnitario(dto.getPrecioUnitario());
        itemCotizacion.setSubtotal(dto.getSubtotal());

        return itemCotizacion;
    }
    private ItemCotizacionDTO createSecondItemDTO(ProductoDTO producto, CreateItemCotizacionDTO createItemCotizacionDTO) {
        ItemCotizacionDTO itemCotizacionDTO = new ItemCotizacionDTO();
        itemCotizacionDTO.setId(2L);
        itemCotizacionDTO.setProducto(producto);
        itemCotizacionDTO.setCantidad(createItemCotizacionDTO.getCantidad());
        itemCotizacionDTO.setPrecioUnitario(producto.getPrecio());
        itemCotizacionDTO.setSubtotal((itemCotizacionDTO.getPrecioUnitario()).multiply(BigDecimal.valueOf(itemCotizacionDTO.getCantidad())));

        return itemCotizacionDTO;
    }

    private Cotizacion createEmptyCotizacion(Cliente cliente) {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(1L);
        cotizacion.setCliente(cliente);
        cotizacion.setItems(new ArrayList<>());

        System.out.println("Imprimir cotizacion vacia : " +cotizacion);
        return cotizacion;
    }

    private BigDecimal calcularTotal(Cotizacion cotizacion, List<CreateItemCotizacionDTO> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (CreateItemCotizacionDTO itemDTO : items) {
            ItemCotizacionDTO nuevoItem = itemCotizacionService.addItemCotizacion(itemDTO, cotizacion.getId());
            total = total.add(nuevoItem.getSubtotal());
        }
        return total;
    }
}
