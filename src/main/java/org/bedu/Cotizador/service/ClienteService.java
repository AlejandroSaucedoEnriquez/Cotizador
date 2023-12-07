package org.bedu.Cotizador.service;

import jakarta.persistence.EntityNotFoundException;
import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateClienteDTO;
import org.bedu.Cotizador.mapper.ClienteMapper;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private ClienteMapper mapper;

    @Autowired
    private CotizacionMapper cotizacionMapper;

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
     * Se recibe un objeto de tipo UpdateClienteDTO y un id, si existe el id en la base de datos se actualiza el cliente.
     * Si no existe se lanza una excepción.
     */
    public ClienteDTO update(UpdateClienteDTO data, long id){
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró un cliente con el ID proporcionado: " + id);
        }

        Optional<Cliente> entity = repository.findById(id);
        if(entity.isPresent()){
            Cliente cliente = repository.save(mapper.toModel(data, entity.get()));
            return mapper.toDTO(cliente);
        } else {
            // Este caso no debería ocurrir debido a la validación anterior, pero por si acaso.
            throw new EntityNotFoundException("No se encontró un cliente con el ID proporcionado: " + id);
        }
    }

    // Obtener por ID
    public ClienteDTO findById(long id) {
        Optional<Cliente> entity = repository.findById(id);
        if (entity.isPresent()) {
            return mapper.toDTO(entity.get());
        } else {
            throw new RuntimeException("Este cliente no existe");
        }
    }

    //Obtener las cotizaciones relacionadas a un cliente
    public List<CotizacionDTO> getCotizacionesByClienteId(long clienteId) {
        Cliente cliente = repository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Cotizacion> cotizaciones = cliente.getCotizaciones();
        return cotizaciones.stream().map(cotizacionMapper::toDTO).collect(Collectors.toList());
    }

    //Eliminar
    public void delete(long id){
        repository.deleteById(id);
    }

}
