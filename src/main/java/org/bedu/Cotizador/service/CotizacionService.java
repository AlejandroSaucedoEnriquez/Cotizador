package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.CreateCotizacionDTO;
import org.bedu.Cotizador.mapper.CotizacionMapper;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CotizacionService {
    @Autowired
    private CotizacionRepository repository;
    @Autowired
    private CotizacionMapper mapper;

    public List<CotizacionDTO> findAll(){
        List<Cotizacion> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }
    public CotizacionDTO save(CreateCotizacionDTO data){
        Cotizacion entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }
}
