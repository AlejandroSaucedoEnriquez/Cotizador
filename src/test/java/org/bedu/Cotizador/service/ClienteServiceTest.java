package org.bedu.Cotizador.service;

import static org.junit.jupiter.api.Assertions.*;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest { 
 
    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteMapper mapper;

    @InjectMocks
    private ClienteService service;

    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test para obtener la lista de clientes")
    public void findAllTest() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Cliente(), new Cliente()));

        List<ClienteDTO> clientes = service.findAll();

        assertNotNull(clientes);
        assertEquals(2, clientes.size());
    }

    @Test
    @DisplayName("Test para guardar un cliente")
    public void saveTest() {
        CreateClienteDTO createClienteDTO = new CreateClienteDTO();
        Cliente cliente = new Cliente();
        ClienteDTO clienteDTO = new ClienteDTO();

        createClienteDTO.setNombre("Juan");
        createClienteDTO.setApellido("Peréz");
        createClienteDTO.setDireccion("Av. Vallarta 1532");
        createClienteDTO.setEmail("juan@example.com");
        createClienteDTO.setTelefono("3315255110");

        when(mapper.toModel(createClienteDTO)).thenReturn(cliente);
        when(repository.save(cliente)).thenReturn(cliente);
        when(mapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO result = service.save(createClienteDTO);

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
    public void updateClientExists() {
        // Arrange
        long id = 1232L;
        UpdateClienteDTO updateDTO = new UpdateClienteDTO();
        Cliente existingEntity = new Cliente();
        ClienteDTO clienteDTO = new ClienteDTO();

        existingEntity.setId(id);
        existingEntity.setNombre("Juan");
        existingEntity.setApellido("Peréz");
        existingEntity.setDireccion("Av. Vallarta 1532");
        existingEntity.setEmail("juan@example.com");
        existingEntity.setTelefono("3315255110");

        updateDTO.setNombre("Carlos");
        updateDTO.setApellido("Rodrigez");
        updateDTO.setDireccion("Av. Vallarta 1532");
        updateDTO.setEmail("carlos@example.com");
        updateDTO.setTelefono("5533658316");

        when(repository.existsById(id)).thenReturn(true);
        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(mapper.toModel(updateDTO, existingEntity)).thenReturn(existingEntity);
        when(repository.save(existingEntity)).thenReturn(existingEntity);
        when(mapper.toDTO(existingEntity)).thenReturn(clienteDTO);

        ClienteDTO result = service.update(updateDTO, id);

        assertNotNull(result);
        assertEquals(clienteDTO.getNombre(), result.getNombre());
        assertEquals(clienteDTO.getApellido(), result.getApellido());
        assertEquals(clienteDTO.getDireccion(), result.getDireccion());
        assertEquals(clienteDTO.getTelefono(), result.getTelefono());
        assertEquals(clienteDTO.getEmail(), result.getEmail());
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
        Cliente cliente = new Cliente();

        cliente.setId(id);
        cliente.setNombre("José");
        cliente.setApellido("Martínez");
        cliente.setDireccion("Av. Patria #1090");
        cliente.setEmail("email@example.com");
        cliente.setTelefono("3312542390");

        Mockito.lenient().doNothing().when(repository).deleteById(id);

        service.delete(id); 
        //Verifica que se haya llamado al método deleteById() del repositorio una vez
        verify(repository, times(1)).deleteById(id);
    }
}