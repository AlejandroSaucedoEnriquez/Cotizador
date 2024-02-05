package org.bedu.Cotizador.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bedu.Cotizador.dto.clienteDTO;
import org.bedu.Cotizador.dto.createDTO.createClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.updateClienteDTO;
import org.bedu.Cotizador.model.cliente;
import org.bedu.Cotizador.repository.clienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class clienteServiceTest {

    @MockBean
    private clienteRepository repository;

    @Autowired
    private clienteService service;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Test para obtener la lista de clientes")
    void findAllTest() {
        List<cliente> clientes = new  LinkedList<>();

        cliente cliente = new cliente();

        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Avenida Vallarta #1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        clientes.add(cliente);

        when(repository.findAll()).thenReturn(clientes);

        List<clienteDTO> result = service.findAll();

        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertEquals(cliente.getId(), result.get(0).getId());
        assertEquals(cliente.getNombre(), result.get(0).getNombre());
        assertEquals(cliente.getApellido(), result.get(0).getApellido());
        assertEquals(cliente.getDireccion(),result.get(0).getDireccion());
        assertEquals(cliente.getEmail(), result.get(0).getEmail());
        assertEquals(cliente.getTelefono(), result.get(0).getTelefono());
    }

    @Test
    @DisplayName("Test para guardar un cliente")
    void saveTest() {
        createClienteDTO createClienteDTO = new createClienteDTO();

        createClienteDTO.setNombre("Juan");
        createClienteDTO.setApellido("Peréz");
        createClienteDTO.setDireccion("Avenida Vallarta #1532");
        createClienteDTO.setEmail("juan@example.com");
        createClienteDTO.setTelefono("3315255110");

        cliente cliente = new cliente();

        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Avenida Vallarta #1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        when(repository.save(any(org.bedu.Cotizador.model.cliente.class))).thenReturn(cliente);

        clienteDTO result = service.save(createClienteDTO);

        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        assertEquals(cliente.getNombre(), result.getNombre());
        assertEquals(cliente.getApellido(), result.getApellido());
        assertEquals(cliente.getDireccion(), result.getDireccion());
        assertEquals(cliente.getEmail(), result.getEmail());
        assertEquals(cliente.getTelefono(), result.getTelefono());
    }

    @Test
    @DisplayName("Test para actualizar un cliente")
    void updateClientExists() {

        long id = 1232L;
        cliente cliente = new cliente();
        cliente.setId(id);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Avenida Vallarta #1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        updateClienteDTO data = new updateClienteDTO();
        data.setNombre("José");
        data.setApellido("Dominguez Peréz");
        data.setDireccion("Avenida Actualizada #2545");
        data.setEmail("josedmgz98@example.com");
        data.setTelefono("3315255444");

        when(repository.findById(anyLong())).thenReturn(Optional.of(cliente));

        service.update(data, id);

        verify(repository, times(1)).save(cliente);
        assertEquals(data.getNombre(), cliente.getNombre());
        assertEquals(data.getApellido(), cliente.getApellido());
        assertEquals(data.getDireccion(), cliente.getDireccion());
        assertEquals(data.getEmail(), cliente.getEmail());
        assertEquals(data.getTelefono(), cliente.getTelefono());
    }

    @Test
    @DisplayName("No se actualizará un cliente si no existe en la base de datos")
    void updateClientDoesNotExist() {
        updateClienteDTO dto = new updateClienteDTO();
        Optional<cliente> dummy = Optional.empty();

        when(repository.findById(anyLong())).thenReturn(dummy);

        assertThrows(EntityNotFoundException.class, () -> service.update(dto, 100));
    }

    @Test
    @DisplayName("Test para eliminar un cliente por su ID")
    void deleteTest() {
        service.delete(38798l);

        verify(repository, times(1)).deleteById(38798l);
    }
}