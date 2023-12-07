package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateProductoDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateProductoDTO;
import org.bedu.Cotizador.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoDTO toDTO(Producto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    Producto toModel(CreateProductoDTO data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    Producto updateModel(UpdateProductoDTO updDTO, @MappingTarget Producto entity);

    @Named("toModelProducto")
    static Producto toModelProducto(Long productoId) {
        if (productoId == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(productoId);
        return producto;
    }
}