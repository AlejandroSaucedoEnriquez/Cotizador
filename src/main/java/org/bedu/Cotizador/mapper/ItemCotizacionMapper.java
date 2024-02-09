package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ItemCotizacionMapper {

    ItemCotizacionDTO toDTO (ItemCotizacion entity);


    @Mapping(target = "producto.id", source = "productoId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cotizacion", ignore = true)
    @Mapping(target = "precioUnitario", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    ItemCotizacion toModel(CreateItemCotizacionDTO dto);

}
