package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateProductoDTO;
import org.bedu.Cotizador.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoDTO toDTO(Producto entity);
    @Mapping(target = "id", ignore = true)
    Producto toModel(CreateProductoDTO dto);
}
