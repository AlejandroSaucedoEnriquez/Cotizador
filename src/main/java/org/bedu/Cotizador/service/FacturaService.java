package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.FacturaDTO;
import org.bedu.Cotizador.dto.createDTO.CreateFacturaDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateFacturaDTO;
import org.bedu.Cotizador.mapper.FacturaMapper;
import org.bedu.Cotizador.model.Factura;
import org.bedu.Cotizador.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class FacturaService {
    @Autowired
    private FacturaRepository repository;
    @Autowired
    private FacturaMapper mapper;

    //Listar
    public List<FacturaDTO> findAll(){
        List<Factura> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }

    //Crear
    public FacturaDTO save(CreateFacturaDTO data){
        Factura entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    /*
     * Actualizar
     * Se recibe un objeto de tipo UpdateClienteDTO y un id, si existe el id en la base de datos se actualiza el cliente.
     * Si no existe se lanza una excepción.
     * 
     */
    public FacturaDTO update(UpdateFacturaDTO data, long id){
        //Se usa optional para evitar nullpointer exceptions
        Optional<Factura> entity = repository.findById(id);
        if(entity.isPresent()){
            /*
                get() regresa el objeto que se encuentra dentro del Optional
                updateModel() regresa un objeto de tipo Cliente con los datos del objeto UpdateClienteDTO
            */
            Factura factura = repository.save(mapper.updateModel(data, entity.get()));
            return mapper.toDTO(factura);
        }else{
            throw new RuntimeException("Esta factura no existe");
        }
    }

    //Eliminar
    public void delete(long id){
        repository.deleteById(id);
    }
}
