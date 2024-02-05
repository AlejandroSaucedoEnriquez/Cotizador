package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.itemCotizacionDTO;
import org.bedu.Cotizador.model.itemCotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface itemCotizacionMapper {

    itemCotizacionDTO toDTO (itemCotizacion entity);

}
