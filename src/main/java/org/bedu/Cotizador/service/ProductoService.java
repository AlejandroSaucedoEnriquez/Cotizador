package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateProductoDTO;
import org.bedu.Cotizador.mapper.ProductoMapper;
import org.bedu.Cotizador.model.Producto;
import org.bedu.Cotizador.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository repository;
    @Autowired
    private ProductoMapper mapper;

    public List<ProductoDTO> findAll(){
        List<Producto> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }
    public ProductoDTO save(CreateProductoDTO data){
        Producto entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }
}
