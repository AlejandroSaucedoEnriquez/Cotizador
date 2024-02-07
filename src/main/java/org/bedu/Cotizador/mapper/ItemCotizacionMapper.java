package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.model.itemCotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ItemCotizacionMapper {

    ItemCotizacionDTO toDTO (itemCotizacion entity);

}
