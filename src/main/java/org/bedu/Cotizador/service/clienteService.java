package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;

import org.bedu.Cotizador.dto.clienteDTO;
import org.bedu.Cotizador.dto.createDTO.createClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.updateClienteDTO;
import org.bedu.Cotizador.mapper.clienteMapper;
import org.bedu.Cotizador.model.cliente;
import org.bedu.Cotizador.repository.clienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class clienteService {

    private final clienteRepository repository;
    private final clienteMapper mapper;

    public clienteService(clienteRepository repository, clienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    //Listar
    public List<clienteDTO> findAll(){
        List<cliente> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }
    //Crear 
    public clienteDTO save(createClienteDTO data){
        cliente entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    /*
     * Actualizar
     * Se recibe un objeto de tipo updateClienteDTO y un id, si existe el id en la base de datos se actualiza el cliente.
     * Si no existe se lanza una excepción.
     */
    public clienteDTO update(updateClienteDTO data, long id){

        Optional<cliente> entity = repository.findById(id);
        if(entity.isPresent()){
            cliente cliente = repository.save(mapper.toModel(data, entity.get()));
            return mapper.toDTO(cliente);
        } else {
            // Este caso no debería ocurrir debido a la validación anterior, pero por si acaso.
            throw new EntityNotFoundException("No se encontró un cliente con el ID proporcionado: " + id);
        }
    }

    // Obtener por ID
    public clienteDTO findById(long id) {
        Optional<cliente> entity = repository.findById(id);
        if (entity.isPresent()) {
            return mapper.toDTO(entity.get());
        } else {
            throw new IllegalArgumentException("Este cliente no existe");
        }
    }

    //Eliminar
    public void delete(long id){
        repository.deleteById(id);
    }
}