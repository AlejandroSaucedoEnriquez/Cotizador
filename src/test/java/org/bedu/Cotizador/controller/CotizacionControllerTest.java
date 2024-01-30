package org.bedu.Cotizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.service.CotizacionService;
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
@WebMvcTest(CotizacionController.class)
public class CotizacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CotizacionService cotizacionService;

    @Test
    public void crearCotizacion_ReturnsCreatedStatus() throws Exception {
        CreateCotizacionDTO createCotizacionDTO = new CreateCotizacionDTO();
        createCotizacionDTO.setClienteId(1L);

        CotizacionDTO cotizacionDTO = new CotizacionDTO();
        given(cotizacionService.crearCotizacion(any(CreateCotizacionDTO.class))).willReturn(cotizacionDTO);

        mockMvc.perform(post("/cotizaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCotizacionDTO)))
                .andExpect(status().isCreated());
    }
}