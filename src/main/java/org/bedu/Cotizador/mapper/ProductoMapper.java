package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateProductoDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateProductoDTO;
import org.bedu.Cotizador.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoDTO toDTO(Producto entity);
    @Mapping(target = "id", ignore = true)
    Producto toModel(CreateProductoDTO data);
    //@MapTarget se usa para indicar el objeto destino de la actualización
    @Mapping(target = "id", ignore = true)
    Producto updateModel(UpdateProductoDTO updDTO, @MappingTarget Producto entity);
}
