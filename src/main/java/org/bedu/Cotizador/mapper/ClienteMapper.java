package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.CreateClienteDTO;
import org.bedu.Cotizador.model.Cliente;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClienteMapper {
    ClienteDTO toDTO(Cliente entity);
    @Mapping(target = "id", ignore = true)
    Cliente toModel(CreateClienteDTO dto);
}
