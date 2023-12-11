package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateItemCotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ItemCotizacionMapper {

    ItemCotizacion toModel (CreateItemCotizacionDTO dto);

    ItemCotizacionDTO toDTO (ItemCotizacion entity);

    ItemCotizacion updateModel (UpdateItemCotizacionDTO updDTO, @MappingTarget ItemCotizacion entity);

}
