package org.bedu.Cotizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateProductoDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateProductoDTO;
import org.bedu.Cotizador.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductoController.class) 
class ProductoControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoService productoService;

    @Test
    void findAllProductos_ReturnsListOfProductos() throws Exception {
        List<ProductoDTO> productos = new ArrayList<>();
        productos.add(new ProductoDTO());
        given(productoService.findAll()).willReturn(productos);

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void saveProducto_ReturnsCreatedStatus() throws Exception {
        CreateProductoDTO createProductoDTO = new CreateProductoDTO();
        createProductoDTO.setNombre("Mancuerna Precor 5kg");
        createProductoDTO.setSku("ManNeg001");
        createProductoDTO.setPrecio(new BigDecimal("500"));
        createProductoDTO.setStock(25);
        createProductoDTO.setDescripcion("Mancuerna hexagoanl negro de cinco kg.");
        createProductoDTO.setCategoria("Accesorios");
        createProductoDTO.setMarca("Precor");
        createProductoDTO.setModelo("sg563");

        ProductoDTO productoDTO = new ProductoDTO();
        given(productoService.save(any(CreateProductoDTO.class))).willReturn(productoDTO);

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateProducto_ReturnsOkStatus() throws Exception {
        long id = 1;
        UpdateProductoDTO updateProductoDTO = new UpdateProductoDTO();
        ProductoDTO productoDTO = new ProductoDTO();
        given(productoService.update(any(UpdateProductoDTO.class), any(Long.class))).willReturn(productoDTO);

        mockMvc.perform(put("/productos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProductoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void findById_ReturnsProducto() throws Exception {
        long id = 1;
        ProductoDTO productoDTO = new ProductoDTO();
        given(productoService.findById(id)).willReturn(productoDTO);

        mockMvc.perform(get("/productos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    void deleteProducto_ReturnsNoContentStatus() throws Exception {
        long id = 1;

        mockMvc.perform(delete("/productos/{id}", id))
                .andExpect(status().isNoContent());
    }
}