package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.productoDTO;
import org.bedu.Cotizador.dto.createDTO.createProductoDTO;
import org.bedu.Cotizador.dto.updateDTO.updateProductoDTO;
import org.bedu.Cotizador.mapper.productoMapper;
import org.bedu.Cotizador.model.producto;
import org.bedu.Cotizador.repository.productoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class productoService {
    private final productoRepository repository;
    private final productoMapper mapper;

    public productoService(productoRepository repository, productoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    //Listar
    public List<productoDTO> findAll(){
        List<producto> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }

    //Crear
    public productoDTO save(createProductoDTO data){
        producto entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    /*
     * Actualizar
     * Se recibe un objeto de tipo updateClienteDTO y un id, si existe el id en la base de datos se actualiza el cliente.
     * Si no existe se lanza una excepción.
     */
    public productoDTO update(updateProductoDTO data, long id){
        //Se usa optional para evitar nullpointer exceptions
        Optional<producto> entity = repository.findById(id);
        if(entity.isPresent()){
            /*
                get() regresa el objeto que se encuentra dentro del Optional
                updateModel() regresa un objeto de tipo cliente con los datos del objeto updateClienteDTO
            */
            producto producto = repository.save(mapper.updateModel(data, entity.get()));
            return mapper.toDTO(producto);
        }else{
            throw new IllegalArgumentException( "Este producto no existe");
        }
    }

    // Obtener por ID
    public productoDTO findById(Long id) {
        Optional<producto> entity = repository.findById(id);
        if (entity.isPresent()) {
            return mapper.toDTO(entity.get());
        } else {
            throw new IllegalArgumentException("Este producto no existe");
        }
    }

    //Eliminar
    public void delete(long id){
        repository.deleteById(id);
    }
}