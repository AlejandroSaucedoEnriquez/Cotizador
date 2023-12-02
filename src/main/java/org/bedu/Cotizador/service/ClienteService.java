package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.createDTO.CreateClienteDTO;
import org.bedu.Cotizador.mapper.ClienteMapper;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private ClienteMapper mapper;

    public List<ClienteDTO> findAll(){
        List<Cliente> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }

    public ClienteDTO save(CreateClienteDTO data){
        Cliente entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }


}
