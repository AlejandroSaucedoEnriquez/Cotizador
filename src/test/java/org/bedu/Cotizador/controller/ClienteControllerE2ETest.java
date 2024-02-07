package org.bedu.Cotizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.createDTO.CreateClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateClienteDTO;
import org.bedu.Cotizador.service.ClienteService;
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
@WebMvcTest(ClienteController.class)
class ClienteControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
   void findAllClientes_ReturnsListOfClientes() throws Exception {
        List<ClienteDTO> clientes = new ArrayList<>();
        clientes.add(new ClienteDTO());
        given(clienteService.findAll()).willReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void saveCliente_ReturnsCreatedStatus() throws Exception {
        CreateClienteDTO createClienteDTO = new CreateClienteDTO();
        createClienteDTO.setNombre("Carlos");
        createClienteDTO.setApellido("Martinez");
        createClienteDTO.setDireccion("Calle Coyoacan #12");
        createClienteDTO.setEmail("carlosm@example.com");
        createClienteDTO.setTelefono("5533221155");

        ClienteDTO clienteDTO = new ClienteDTO();
        given(clienteService.save(any(CreateClienteDTO.class))).willReturn(clienteDTO);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClienteDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCliente_ReturnsOkStatus() throws Exception {
        long id = 1;
        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO();
        ClienteDTO clienteDTO = new ClienteDTO();
        given(clienteService.update(any(UpdateClienteDTO.class), any(Long.class))).willReturn(clienteDTO);

        mockMvc.perform(put("/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateClienteDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void findById_ReturnsCliente() throws Exception {
        long id = 1;
        ClienteDTO clienteDTO = new ClienteDTO();
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