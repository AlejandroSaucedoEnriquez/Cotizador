package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.FacturaDTO;
import org.bedu.Cotizador.dto.createDTO.CreateFacturaDTO;
import org.bedu.Cotizador.mapper.FacturaMapper;
import org.bedu.Cotizador.model.Factura;
import org.bedu.Cotizador.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FacturaService {
    @Autowired
    private FacturaRepository repository;
    @Autowired
    private FacturaMapper mapper;

    public List<FacturaDTO> findAll(){
        List<Factura> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }
    public FacturaDTO save(CreateFacturaDTO data){
        Factura entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }
}
