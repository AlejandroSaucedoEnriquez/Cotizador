package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.createDTO.CreateClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateClienteDTO;
import org.bedu.Cotizador.model.Cliente;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import jakarta.validation.Valid;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClienteMapper {
    ClienteDTO toDTO(Cliente entity);
    @Mapping(target = "id", ignore = true)
    Cliente toModel(org.bedu.Cotizador.dto.@Valid CreateClienteDTO data);
    //@MapTarget se usa para indicar el objeto destino de la actualización
    @Mapping(target = "id", ignore = true)
    Cliente updateModel(UpdateClienteDTO updDTO, @MappingTarget Cliente entity);
}
