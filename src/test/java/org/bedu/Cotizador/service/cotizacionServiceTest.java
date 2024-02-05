package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import org.bedu.Cotizador.dto.itemCotizacionDTO;
import org.bedu.Cotizador.dto.productoDTO;
import org.bedu.Cotizador.dto.createDTO.createCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.createItemCotizacionDTO;
import org.bedu.Cotizador.mapper.itemCotizacionMapper;
import org.bedu.Cotizador.model.cliente;
import org.bedu.Cotizador.model.cotizacion;
import org.bedu.Cotizador.model.itemCotizacion;
import org.bedu.Cotizador.model.producto;
import org.bedu.Cotizador.repository.cotizacionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
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
class cotizacionServiceTest {

    @MockBean
    private cotizacionRepository repository;

    @MockBean
    private org.bedu.Cotizador.repository.clienteRepository clienteRepository;

    @MockBean
    private org.bedu.Cotizador.repository.itemCotizacionRepository itemCotizacionRepository;

    @InjectMocks
    private cotizacionService service;

    @InjectMocks
    private org.bedu.Cotizador.service.itemCotizacionService itemCotizacionService;

    @MockBean
    private org.bedu.Cotizador.repository.productoRepository productoRepository;

    @Autowired
    private itemCotizacionMapper mapper;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    /*
    @Test
    @DisplayName("Test de error cuando no encuentra el cliente")
    void crearCotizacionDoesNotCliente(){
        createCotizacionDTO createDTO = new createCotizacionDTO();

        Optional<cliente> dummy = Optional.empty();

        when(clienteRepository.findById(anyLong())).thenReturn(dummy);

        assertThrows(EntityNotFoundException.class, ()-> service.crearCotizacion(createDTO));
    }

    @Test
    @DisplayName("Test crea una cotizacion con dos productos")
    void crearCotizacionOneProducts() {
        long id = 1;
        cliente cliente = createCliente(id);

        producto producto1 = new producto();

        producto1.setId(1L);
        producto1.setNombre("Mancuerna Precor 5kg");
        producto1.setSku("ManNeg001");
        producto1.setPrecio(new BigDecimal("500"));
        producto1.setStock(25);
        producto1.setDescripcion("Mancuerna hexagonal negro de cinco kg");
        producto1.setCategoria("Accesorios");
        producto1.setMarca("Precor");
        producto1.setModelo("sg563");

        List<createItemCotizacionDTO> createItemList = new ArrayList<>();
        createItemCotizacionDTO createItemCotizacionDTO1 = new createItemCotizacionDTO(producto1.getId(), 4);
        createItemList.add(createItemCotizacionDTO1);

        itemCotizacionDTO itemCotizacionDTO1 = createFirstItemDTO(productoToDTO(producto1), createItemCotizacionDTO1);

        cotizacion emptyCotizacion = createEmptyCotizacion(cliente);

        createCotizacionDTO createCotizacionDTO = new createCotizacionDTO(cliente.getId(), createItemList);

        List<itemCotizacion> itemCotizacions = new ArrayList<>();

        cotizacion cotizacion = new cotizacion();
        cotizacion.setId(0L);
        cotizacion.setCliente(cliente);

        itemCotizacion itemCotizacion1 = DtoToEntity(itemCotizacionDTO1, cotizacion, producto1);
        itemCotizacions.add(itemCotizacion1);

        BigDecimal total = BigDecimal.ZERO;
        for (itemCotizacion item : itemCotizacions) {
            total = total.add(item.getSubtotal());
        }

        cotizacion.setItems(itemCotizacions);
        cotizacion.setFecha(LocalDate.now());
        cotizacion.setTotal(total);

        cotizacionDTO cotizacionDTO = new cotizacionDTO();
        cotizacionDTO.setId(cotizacion.getId());
        cotizacionDTO.setClienteId(cotizacion.getCliente().getId());

        List<itemCotizacionDTO> items = new ArrayList<>();
        items.add(itemCotizacionDTO1);

        cotizacionDTO.setItems(items);
        cotizacionDTO.setFecha(cotizacion.getFecha());
        cotizacionDTO.setTotal(cotizacion.getTotal());

        System.out.println("Imprimir cotizacion : "+cotizacion);
        System.out.println("Imprimir item "+ itemCotizacion1);

        emptyCotizacion.setItems(itemCotizacions);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(repository.saveAndFlush(any(cotizacion.class))).thenReturn(emptyCotizacion);

        when(itemCotizacionService.cotizacionRepository.findById(anyLong())).thenReturn(Optional.of(emptyCotizacion));
        when(itemCotizacionService.productoRepository.findById(anyLong())).thenReturn(Optional.of(producto1));
        when(itemCotizacionRepository.save(any(itemCotizacion.class))).thenReturn(itemCotizacion1);

        when(itemCotizacionService.addItemCotizacion(any(createItemCotizacionDTO.class), eq(0L))).thenReturn(itemCotizacionDTO1);

        when(repository.save(any(cotizacion.class))).thenReturn(cotizacion);

        cotizacionDTO result = service.crearCotizacion(createCotizacionDTO);

        assertNotNull(result);
    }*/

    private cliente createCliente(long id) {
        cliente cliente = new cliente();
        cliente.setId(id);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Avenida Vallarta #1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        return cliente;
    }

    private producto createFirstProducto() {
        producto producto1 = new producto();

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

    private producto createSecondProducto() {
        producto producto2 = new producto();

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

    private productoDTO productoToDTO (producto producto) {
        productoDTO productoDTO = new productoDTO();

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

    private itemCotizacionDTO createFirstItemDTO(productoDTO producto, createItemCotizacionDTO createItemCotizacionDTO) {
        itemCotizacionDTO itemCotizacionDTO = new itemCotizacionDTO();
        itemCotizacionDTO.setId(1L);
        itemCotizacionDTO.setProducto(producto);
        itemCotizacionDTO.setCantidad(createItemCotizacionDTO.getCantidad());
        itemCotizacionDTO.setPrecioUnitario(producto.getPrecio());
        itemCotizacionDTO.setSubtotal((itemCotizacionDTO.getPrecioUnitario()).multiply(BigDecimal.valueOf(itemCotizacionDTO.getCantidad())));

        return itemCotizacionDTO;
    }

    private itemCotizacion DtoToEntity (itemCotizacionDTO dto, cotizacion cotizacion, producto producto) {
        itemCotizacion itemCotizacion = new itemCotizacion();
        itemCotizacion.setId(dto.getId());
        itemCotizacion.setCotizacion(cotizacion);
        itemCotizacion.setProducto(producto);
        itemCotizacion.setCantidad(dto.getCantidad());
        itemCotizacion.setPrecioUnitario(dto.getPrecioUnitario());
        itemCotizacion.setSubtotal(dto.getSubtotal());

        return itemCotizacion;
    }
    private itemCotizacionDTO createSecondItemDTO(productoDTO producto, createItemCotizacionDTO createItemCotizacionDTO) {
        itemCotizacionDTO itemCotizacionDTO = new itemCotizacionDTO();
        itemCotizacionDTO.setId(2L);
        itemCotizacionDTO.setProducto(producto);
        itemCotizacionDTO.setCantidad(createItemCotizacionDTO.getCantidad());
        itemCotizacionDTO.setPrecioUnitario(producto.getPrecio());
        itemCotizacionDTO.setSubtotal((itemCotizacionDTO.getPrecioUnitario()).multiply(BigDecimal.valueOf(itemCotizacionDTO.getCantidad())));

        return itemCotizacionDTO;
    }

    private cotizacion createEmptyCotizacion(cliente cliente) {
        List<itemCotizacion> items = new ArrayList<>();

        cotizacion cotizacion = new cotizacion();
        cotizacion.setCliente(cliente);
        cotizacion.setItems(items);

        System.out.println("Imprimir cotizacion vacia : " +cotizacion);
        return cotizacion;
    }

}
