package org.bedu.Cotizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.Cotizador.dto.clienteDTO;
import org.bedu.Cotizador.dto.createDTO.createClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.updateClienteDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(clienteController.class)
class clienteControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private org.bedu.Cotizador.service.clienteService clienteService;

    @Test
   void findAllClientes_ReturnsListOfClientes() throws Exception {
        List<clienteDTO> clientes = new ArrayList<>();
        clientes.add(new clienteDTO());
        given(clienteService.findAll()).willReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void saveCliente_ReturnsCreatedStatus() throws Exception {
        createClienteDTO createClienteDTO = new createClienteDTO();
        createClienteDTO.setNombre("Carlos");
        createClienteDTO.setApellido("Martinez");
        createClienteDTO.setDireccion("Calle Coyoacan #12");
        createClienteDTO.setEmail("carlosm@example.com");
        createClienteDTO.setTelefono("5533221155");

        clienteDTO clienteDTO = new clienteDTO();
        given(clienteService.save(any(org.bedu.Cotizador.dto.createDTO.createClienteDTO.class))).willReturn(clienteDTO);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClienteDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCliente_ReturnsOkStatus() throws Exception {
        long id = 1;
        updateClienteDTO updateClienteDTO = new updateClienteDTO();
        clienteDTO clienteDTO = new clienteDTO();
        given(clienteService.update(any(org.bedu.Cotizador.dto.updateDTO.updateClienteDTO.class), any(Long.class))).willReturn(clienteDTO);

        mockMvc.perform(put("/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateClienteDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void findById_ReturnsCliente() throws Exception {
        long id = 1;
        clienteDTO clienteDTO = new clienteDTO();
        given(clienteService.findById(id)).willReturn(clienteDTO);

        mockMvc.perform(get("/clientes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    void deleteCliente_ReturnsNoContentStatus() throws Exception {
        long id = 1;

        mockMvc.perform(delete("/clientes/{id}", id))
                .andExpect(status().isNoContent());
    }
}