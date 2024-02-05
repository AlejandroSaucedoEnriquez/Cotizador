package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.productoDTO;
import org.bedu.Cotizador.dto.createDTO.createProductoDTO;
import org.bedu.Cotizador.dto.updateDTO.updateProductoDTO;
import org.bedu.Cotizador.model.producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface productoMapper {

    productoDTO toDTO(producto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itemCotizacion", ignore = true)
    producto toModel(createProductoDTO data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itemCotizacion", ignore = true)
    producto updateModel(updateProductoDTO updDTO, @MappingTarget producto entity);
}