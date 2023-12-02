package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CotizacionMapper {
    CotizacionDTO toDTO(Cotizacion entity);
    @Mapping(target = "id", ignore = true)
    Cotizacion toModel(CreateCotizacionDTO dto);

}
