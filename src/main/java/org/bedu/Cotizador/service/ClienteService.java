package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;

import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.createDTO.CreateClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateClienteDTO;
import org.bedu.Cotizador.mapper.ClienteMapper;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    //Listar
    public List<ClienteDTO> findAll(){
        List<Cliente> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }
    //Crear 
    public ClienteDTO save(CreateClienteDTO data){
        Cliente entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    /*
     * Actualizar
     * Se recibe un objeto de tipo UpdateClienteDTO y un id, si existe el id en la base de datos se actualiza el Cliente.
     * Si no existe se lanza una excepción.
     */
    public ClienteDTO update(UpdateClienteDTO data, long id){

        Optional<Cliente> entity = repository.findById(id);
        if(entity.isPresent()){
            Cliente cliente = repository.save(mapper.toModel(data, entity.get()));
            return mapper.toDTO(cliente);
        } else {
            // Este caso no debería ocurrir debido a la validación anterior, pero por si acaso.
            throw new EntityNotFoundException("No se encontró un Cliente con el ID proporcionado: " + id);
        }
    }

    // Obtener por ID
    public ClienteDTO findById(long id) {
        Optional<Cliente> entity = repository.findById(id);
        if (entity.isPresent()) {
            return mapper.toDTO(entity.get());
        } else {
            throw new IllegalArgumentException("Este Cliente no existe");
        }
    }

    //Eliminar
    public void delete(long id){
        repository.deleteById(id);
    }
}