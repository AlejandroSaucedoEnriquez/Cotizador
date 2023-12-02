package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.model.Cotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CotizacionMapper {
    CotizacionDTO toDTO(Cotizacion entity);

    @Mapping(target = "id", ignore = true)
    Cotizacion toModel(CreateCotizacionDTO dto);

}
