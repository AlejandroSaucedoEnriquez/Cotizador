package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CotizacionMapper {

    @Mapping(target = "clienteId", source = "cliente.id")
    CotizacionDTO toDTO (Cotizacion entity);


    @Mapping(target = "cliente.id", source = "clienteId")
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "total", ignore = true)
    Cotizacion toModel(CreateCotizacionDTO dto);
}
