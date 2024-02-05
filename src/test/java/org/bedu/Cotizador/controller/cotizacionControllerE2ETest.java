package org.bedu.Cotizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.Cotizador.dto.cotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.createCotizacionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(cotizacionController.class)
class cotizacionControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private org.bedu.Cotizador.service.cotizacionService cotizacionService;

    @Test
    void crearCotizacion_ReturnsCreatedStatus() throws Exception {
        createCotizacionDTO createCotizacionDTO = new createCotizacionDTO();
        createCotizacionDTO.setClienteId(1L);

        cotizacionDTO cotizacionDTO = new cotizacionDTO();
        given(cotizacionService.crearCotizacion(any(org.bedu.Cotizador.dto.createDTO.createCotizacionDTO.class))).willReturn(cotizacionDTO);

        mockMvc.perform(post("/cotizaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCotizacionDTO)))
                .andExpect(status().isCreated());
    }
}