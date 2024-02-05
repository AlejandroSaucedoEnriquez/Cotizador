package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.cotizacionDTO;
import org.bedu.Cotizador.model.cotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface cotizacionMapper {

    @Mapping(target = "clienteId", source = "cliente.id")
    cotizacionDTO toDTO (cotizacion entity);
}
