package org.bedu.Cotizador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.createDTO.CreateClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateClienteDTO;
import org.bedu.Cotizador.mapper.ClienteMapper;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest { 
 
    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteMapper mapper;

    @InjectMocks
    private ClienteService service;

    @Test
    @DisplayName("Test para obtener la lista de clientes")
    public void findAllTest() {
        Cliente cliente = new Cliente();
        ClienteDTO clienteDTO = new ClienteDTO();

        when(repository.findAll()).thenReturn(Arrays.asList(cliente));
        when(mapper.toDTO(cliente)).thenReturn(clienteDTO);

        List<ClienteDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals(clienteDTO, result.get(0));
    }

    @Test
    @DisplayName("Test para guardar un cliente")
    public void saveTest() {
        CreateClienteDTO createClienteDTO = new CreateClienteDTO();
        createClienteDTO.setNombre("Juan");
        createClienteDTO.setApellido("Peréz");
        createClienteDTO.setDireccion("Av. Vallarta 1532");
        createClienteDTO.setEmail("juan@example.com");
        createClienteDTO.setTelefono("3315255110");
 
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Av. Vallarta 1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");
        
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Juan");
        clienteDTO.setApellido("Peréz");
        clienteDTO.setDireccion("Av. Vallarta 1532");
        clienteDTO.setEmail("juan@example.com");
        clienteDTO.setTelefono("3315255110");

        when(mapper.toModel(createClienteDTO)).thenReturn(cliente);
        when(repository.save(cliente)).thenReturn(cliente);
        when(mapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO result = service.save(createClienteDTO);

        assertEquals(clienteDTO, result);
        assertEquals(clienteDTO.getId(), result.getId());
        assertEquals(clienteDTO.getNombre(), result.getNombre());
        assertEquals(clienteDTO.getApellido(), result.getApellido());
        assertEquals(clienteDTO.getDireccion(), result.getDireccion());
        assertEquals(clienteDTO.getEmail(), result.getEmail());
        assertEquals(clienteDTO.getTelefono(), result.getTelefono());
    }

    @Test
    @DisplayName("Test para actualizar un cliente")
    public void updateClientExists() {
        // Arrange
        long id = 1232; 
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Juan");
        cliente.setApellido("Peréz");
        cliente.setDireccion("Av. Vallarta 1532");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("3315255110");

        ClienteDTO clienteDTO = new ClienteDTO(); 
        clienteDTO.setId(id);
        clienteDTO.setNombre("José");
        clienteDTO.setApellido("Dominguez Peréz");
        clienteDTO.setDireccion("Av. Actualizada #2545");
        clienteDTO.setEmail("josedmgz98@example.com");
        clienteDTO.setTelefono("3315255444");
 
        UpdateClienteDTO data = new UpdateClienteDTO();
        data.setNombre("José");
        data.setApellido("Dominguez Peréz");
        data.setDireccion("Av. Actualizada #2545");
        data.setEmail("josedmgz98@example.com");
        data.setTelefono("3315255444");

        when(repository.existsById(id)).thenReturn(true);
        when(repository.findById(id)).thenReturn(Optional.of(cliente));
        when(mapper.toModel(data, cliente)).thenReturn(cliente);
        when(repository.save(cliente)).thenReturn(cliente);
        when(mapper.toDTO(cliente)).thenReturn(clienteDTO);
 

        ClienteDTO result = service.update(data, id);

        assertEquals(clienteDTO, result); 
        assertEquals(clienteDTO.getNombre(), result.getNombre()); 
        assertEquals(clienteDTO.getApellido(), result.getApellido());
        assertEquals(clienteDTO.getDireccion(), result.getDireccion());
        assertEquals(clienteDTO.getEmail(), result.getEmail());
        assertEquals(clienteDTO.getTelefono(), result.getTelefono());
    }

    @Test
    @DisplayName("No se actualizará un cliente si no existe en la base de datos")
    public void updateClientDoesNotExist() {
        long id = 123123;
        UpdateClienteDTO data = new UpdateClienteDTO(); 
        
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.update(data, id));
    }

    @Test
    @DisplayName("Test para eliminar un cliente por su ID")
    public void deleteTest() {  
        long id = 123;
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("José");
        clienteDTO.setApellido("Martínez");
        clienteDTO.setDireccion("Av. Patria #1090");
        clienteDTO.setEmail("email@example.com");
        clienteDTO.setTelefono("3312542390");

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("José");
        cliente.setApellido("Martínez");
        cliente.setDireccion("Av. Patria #1090");
        cliente.setEmail("email@example.com");
        cliente.setTelefono("3312542390");

        CreateClienteDTO createClienteDTO = new CreateClienteDTO();
        createClienteDTO.setNombre("José");
        createClienteDTO.setApellido("Martínez");
        createClienteDTO.setDireccion("Av. Patria #1090");
        createClienteDTO.setEmail("email@example.com");
        createClienteDTO.setTelefono("3312542390");

        when(mapper.toModel(createClienteDTO)).thenReturn(cliente);
        when(repository.save(cliente)).thenReturn(cliente);
        when(mapper.toDTO(cliente)).thenReturn(clienteDTO);
        doNothing().when(repository).deleteById(id);
 
        ClienteDTO createdClienteDTO = service.save(createClienteDTO);
        assertEquals(clienteDTO, createdClienteDTO);
        service.delete(id); 
        //Verifica que se haya llamado al método deleteById() del repositorio una vez
        verify(repository, times(1)).deleteById(id);
    }
}