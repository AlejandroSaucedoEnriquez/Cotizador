package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateProductoDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateProductoDTO;
import org.bedu.Cotizador.mapper.ProductoMapper;
import org.bedu.Cotizador.model.Producto;
import org.bedu.Cotizador.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    public ProductoService(ProductoRepository repository, ProductoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    //Listar
    public List<ProductoDTO> findAll(){
        List<Producto> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }

    //Crear
    public ProductoDTO save(CreateProductoDTO data){
        Producto entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    /*
     * Actualizar
     * Se recibe un objeto de tipo UpdateClienteDTO y un id, si existe el id en la base de datos se actualiza el Cliente.
     * Si no existe se lanza una excepción.
     */
    public ProductoDTO update(UpdateProductoDTO data, long id){
        //Se usa optional para evitar nullpointer exceptions
        Optional<Producto> entity = repository.findById(id);
        if(entity.isPresent()){
            /*
                get() regresa el objeto que se encuentra dentro del Optional
                updateModel() regresa un objeto de tipo Cliente con los datos del objeto UpdateClienteDTO
            */
            Producto producto = repository.save(mapper.updateModel(data, entity.get()));
            return mapper.toDTO(producto);
        }else{
            throw new IllegalArgumentException( "Este Producto no existe");
        }
    }

    // Obtener por ID
    public ProductoDTO findById(Long id) {
        Optional<Producto> entity = repository.findById(id);
        if (entity.isPresent()) {
            return mapper.toDTO(entity.get());
        } else {
            throw new IllegalArgumentException("Este Producto no existe");
        }
    }

    //Eliminar
    public void delete(long id){
        repository.deleteById(id);
    }
}