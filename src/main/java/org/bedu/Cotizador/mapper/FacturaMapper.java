package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.FacturaDTO;
import org.bedu.Cotizador.dto.createDTO.CreateFacturaDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateFacturaDTO;
import org.bedu.Cotizador.model.Factura;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FacturaMapper {
    FacturaDTO toDTO(Factura entity);
    @Mapping(target = "id", ignore = true)
    Factura toModel(CreateFacturaDTO dto);
    //@MapTarget se usa para indicar el objeto destino de la actualización
    @Mapping(target = "id", ignore = true)
    Factura updateModel(UpdateFacturaDTO updDTO, @MappingTarget Factura entity);
}
