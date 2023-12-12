package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CotizacionMapper {

    @Mapping(target = "clienteId", source = "cliente.id")
    CotizacionDTO toDTO (Cotizacion entity);


}
