package org.bedu.Cotizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.Cotizador.dto.productoDTO;
import org.bedu.Cotizador.dto.createDTO.createProductoDTO;
import org.bedu.Cotizador.dto.updateDTO.updateProductoDTO;
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
@WebMvcTest(productoController.class)
class productoControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private org.bedu.Cotizador.service.productoService productoService;

    @Test
    void findAllProductos_ReturnsListOfProductos() throws Exception {
        List<productoDTO> productos = new ArrayList<>();
        productos.add(new productoDTO());
        given(productoService.findAll()).willReturn(productos);

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void saveProducto_ReturnsCreatedStatus() throws Exception {
        createProductoDTO createProductoDTO = new createProductoDTO();
        createProductoDTO.setNombre("Mancuerna Precor 5kg");
        createProductoDTO.setSku("ManNeg001");
        createProductoDTO.setPrecio(new BigDecimal("500"));
        createProductoDTO.setStock(25);
        createProductoDTO.setDescripcion("Mancuerna hexagonal negro de cinco kg");
        createProductoDTO.setCategoria("Accesorios");
        createProductoDTO.setMarca("Precor");
        createProductoDTO.setModelo("sg563");

        productoDTO productoDTO = new productoDTO();
        given(productoService.save(any(org.bedu.Cotizador.dto.createDTO.createProductoDTO.class))).willReturn(productoDTO);

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateProducto_ReturnsOkStatus() throws Exception {
        long id = 1;
        updateProductoDTO updateProductoDTO = new updateProductoDTO();
        productoDTO productoDTO = new productoDTO();
        given(productoService.update(any(org.bedu.Cotizador.dto.updateDTO.updateProductoDTO.class), any(Long.class))).willReturn(productoDTO);

        mockMvc.perform(put("/productos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProductoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void findById_ReturnsProducto() throws Exception {
        long id = 1;
        productoDTO productoDTO = new productoDTO();
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