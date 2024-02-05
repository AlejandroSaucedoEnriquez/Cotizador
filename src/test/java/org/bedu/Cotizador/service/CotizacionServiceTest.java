package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.mapper.ItemCotizacionMapper;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.bedu.Cotizador.model.Producto;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.bedu.Cotizador.repository.ItemCotizacionRepository;
import org.bedu.Cotizador.repository.ProductoRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
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

    @InjectMocks
    private CotizacionService service;

    @InjectMocks
    private ItemCotizacionService itemCotizacionService;

    @MockBean
    private ProductoRepository productoRepository;

    @Autowired
    private ItemCotizacionMapper mapper;

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
    void crearCotizacionOneProducts() {
        long id = 1;
        Cliente cliente = createCliente(id);

        Producto producto1 = new Producto();

        producto1.setId(1L);
        producto1.setNombre("Mancuerna Precor 5kg");
        producto1.setSku("ManNeg001");
        producto1.setPrecio(new BigDecimal("500"));
        producto1.setStock(25);
        producto1.setDescripcion("Mancuerna hexagonal negro de cinco kg");
        producto1.setCategoria("Accesorios");
        producto1.setMarca("Precor");
        producto1.setModelo("sg563");

        List<CreateItemCotizacionDTO> createItemList = new ArrayList<>();
        CreateItemCotizacionDTO createItemCotizacionDTO1 = new CreateItemCotizacionDTO(producto1.getId(), 4);
        createItemList.add(createItemCotizacionDTO1);

        ItemCotizacionDTO itemCotizacionDTO1 = createFirstItemDTO(productoToDTO(producto1), createItemCotizacionDTO1);

        Cotizacion emptyCotizacion = createEmptyCotizacion(cliente);

        CreateCotizacionDTO createCotizacionDTO = new CreateCotizacionDTO(cliente.getId(), createItemList);

        List<ItemCotizacion> itemCotizacions = new ArrayList<>();

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(0L);
        cotizacion.setCliente(cliente);

        ItemCotizacion itemCotizacion1 = DtoToEntity(itemCotizacionDTO1, cotizacion, producto1);
        itemCotizacions.add(itemCotizacion1);

        BigDecimal total = BigDecimal.ZERO;
        for (ItemCotizacion item : itemCotizacions) {
            total = total.add(item.getSubtotal());
        }

        cotizacion.setItems(itemCotizacions);
        cotizacion.setFecha(LocalDate.now());
        cotizacion.setTotal(total);

        CotizacionDTO cotizacionDTO = new CotizacionDTO();
        cotizacionDTO.setId(cotizacion.getId());
        cotizacionDTO.setClienteId(cotizacion.getCliente().getId());

        List<ItemCotizacionDTO> items = new ArrayList<>();
        items.add(itemCotizacionDTO1);

        cotizacionDTO.setItems(items);
        cotizacionDTO.setFecha(cotizacion.getFecha());
        cotizacionDTO.setTotal(cotizacion.getTotal());

        System.out.println("Imprimir cotizacion : "+cotizacion);
        System.out.println("Imprimir item "+ itemCotizacion1);

        emptyCotizacion.setItems(itemCotizacions);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(repository.saveAndFlush(any(Cotizacion.class))).thenReturn(emptyCotizacion);

        when(itemCotizacionService.cotizacionRepository.findById(anyLong())).thenReturn(Optional.of(emptyCotizacion));
        when(itemCotizacionService.productoRepository.findById(anyLong())).thenReturn(Optional.of(producto1));
        when(itemCotizacionRepository.save(any(ItemCotizacion.class))).thenReturn(itemCotizacion1);

        when(itemCotizacionService.addItemCotizacion(any(CreateItemCotizacionDTO.class), eq(0L))).thenReturn(itemCotizacionDTO1);

        when(repository.save(any(Cotizacion.class))).thenReturn(cotizacion);

        CotizacionDTO result = service.crearCotizacion(createCotizacionDTO);

        assertNotNull(result);
    }

    private Cliente createCliente(long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Avenida Vallarta #1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        return cliente;
    }

    private Producto createFirstProducto() {
        Producto producto1 = new Producto();

        producto1.setId(1L);
        producto1.setNombre("Mancuerna Precor 5 kg");
        producto1.setSku("28394");
        producto1.setPrecio(new BigDecimal("500"));
        producto1.setStock(25);
        producto1.setDescripcion("Mancuerna hexagonal negro de cinco kg");
        producto1.setCategoria("Accesorios");
        producto1.setMarca("Precor");
        producto1.setModelo("sg563");

        return producto1;
    }

    private Producto createSecondProducto() {
        Producto producto2 = new Producto();

        producto2.setId(2L);
        producto2.setNombre("Mancuerna Life fitness 10 kg");
        producto2.setSku("ManNeg002");
        producto2.setPrecio(new BigDecimal("650"));
        producto2.setStock(25);
        producto2.setDescripcion("Mancuerna hexagonal negro de diez kg");
        producto2.setCategoria("Peso libre.");
        producto2.setMarca("Life fitness");
        producto2.setModelo("sg573");

        return producto2;
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
        List<ItemCotizacion> items = new ArrayList<>();

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setCliente(cliente);
        cotizacion.setItems(items);

        System.out.println("Imprimir cotizacion vacia : " +cotizacion);
        return cotizacion;
    }

}
