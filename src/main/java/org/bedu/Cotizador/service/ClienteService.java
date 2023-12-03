package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateClienteDTO;
import org.bedu.Cotizador.mapper.ClienteMapper;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private ClienteMapper mapper;

    //Listar
    public List<ClienteDTO> findAll(){
        List<Cliente> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }
    //Crear 
    public ClienteDTO save(org.bedu.Cotizador.dto.@Valid CreateClienteDTO data){
        Cliente entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    /*
     * Actualizar
     * Se recibe un objeto de tipo UpdateClienteDTO y un id, si existe el id en la base de datos se actualiza el cliente.
     * Si no existe se lanza una excepción.
     */
    public ClienteDTO update(UpdateClienteDTO data, long id){
        //Se usa optional para evitar nullpointer exceptions
        Optional<Cliente> entity = repository.findById(id);
        if(entity.isPresent()){
            /*
                get() regresa el objeto que se encuentra dentro del Optional
                updateModel() regresa un objeto de tipo Cliente con los datos del objeto UpdateClienteDTO
            */
            Cliente cliente = repository.save(mapper.updateModel(data, entity.get()));
            return mapper.toDTO(cliente);
        }else{
            throw new RuntimeException("Este cliente no existe");
        }
    }

    //Eliminar
    public void delete(long id){
        repository.deleteById(id);
    }

}
