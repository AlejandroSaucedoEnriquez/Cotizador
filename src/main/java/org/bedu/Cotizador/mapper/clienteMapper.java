package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.clienteDTO;
import org.bedu.Cotizador.dto.createDTO.createClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.updateClienteDTO;
import org.bedu.Cotizador.model.cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface clienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cotizaciones", ignore = true)
    cliente toModel(createClienteDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cotizaciones", ignore = true)
    cliente toModel(updateClienteDTO dto, @MappingTarget cliente cliente);

    clienteDTO toDTO(cliente entity);
}