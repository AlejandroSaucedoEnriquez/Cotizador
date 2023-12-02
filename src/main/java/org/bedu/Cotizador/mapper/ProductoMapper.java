package org.bedu.Cotizador.mapper;

<<<<<<< HEAD
public class ProductoMapper {
    
=======
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
>>>>>>> 0a5649c44af902a15eab2ab307d2c88a1d68c8cc
}
