package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.ClienteDTO;
import org.bedu.Cotizador.dto.createDTO.CreateClienteDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateClienteDTO;
import org.bedu.Cotizador.model.Cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cotizaciones", ignore = true)
    Cliente toModel(CreateClienteDTO dto);

    @Mapping(target = "cotizaciones", ignore = true)
    Cliente toModel(UpdateClienteDTO dto, @MappingTarget Cliente cliente);

    ClienteDTO toDTO(Cliente entity);

    // Método adicional para mapear Long a Cliente
    @Named("toModel")
    static Cliente toModel(Long clienteId) {
        if (clienteId == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        return cliente;
    }
}

