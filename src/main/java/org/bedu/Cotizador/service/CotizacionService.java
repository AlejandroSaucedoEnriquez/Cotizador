package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateCotizacionDTO;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CotizacionService {
    @Autowired
    private CotizacionRepository repository;
    @Autowired
    private CotizacionMapper mapper;

    //Listar
    public List<CotizacionDTO> findAll(){
        List<Cotizacion> datos = repository.findAll();
        return datos.stream().map(mapper::toDTO).toList();
    }

    //Crear
    public CotizacionDTO save(CreateCotizacionDTO data){
        Cotizacion entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    /*
     * Actualizar
     * Se recibe un objeto de tipo UpdateClienteDTO y un id, si existe el id en la base de datos se actualiza el cliente.
     * Si no existe se lanza una excepción.
     */
    public CotizacionDTO update(UpdateCotizacionDTO data, long id){
        //Se usa optional para evitar nullpointer exceptions
        Optional<Cotizacion> entity = repository.findById(id);
        if(entity.isPresent()){
            /*
                get() regresa el objeto que se encuentra dentro del Optional
                updateModel() regresa un objeto de tipo Cliente con los datos del objeto UpdateClienteDTO
            */
            Cotizacion cotizacion = repository.save(mapper.updateModel(data, entity.get()));
            return mapper.toDTO(cotizacion);
        }else{
            throw new RuntimeException("Esta cotizacion no existe");
        }
    }
    
    //Eliminar
    public void delete(long id){
        repository.deleteById(id);
    }
}
