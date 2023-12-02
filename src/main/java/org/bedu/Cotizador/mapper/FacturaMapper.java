package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.model.Factura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacturaMapper {
    FacturaDTO toDTO(Factura entity);
    @Mapping(target = "id", ignore = true)
    Factura toModel(CreateFacturaDTO dto);
}
