package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateCotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CotizacionMapper {
    CotizacionDTO toDTO(Cotizacion entity);
    @Mapping(target = "id", ignore = true)
    Cotizacion toModel(CreateCotizacionDTO dto);
    //@MapTarget se usa para indicar el objeto destino de la actualización
    @Mapping(target = "id", ignore = true)
    Cotizacion updateModel(UpdateCotizacionDTO updDTO, @MappingTarget Cotizacion entity);
}
