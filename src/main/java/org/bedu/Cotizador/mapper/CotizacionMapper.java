package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateCotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

// Se agrefo "uses = {ClienteMapper.class, ProductoMapper.class}"
// Para indicar que se van usar los Mapper para la conversion correcta de la cotizacion
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {ClienteMapper.class, ProductoMapper.class})
public interface CotizacionMapper {
    CotizacionDTO toDTO(Cotizacion entity);
    @Mapping(target = "id", ignore = true)
    Cotizacion toModel(CreateCotizacionDTO dto);
    //@MapTarget se usa para indicar el objeto destino de la actualización
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factura", ignore = true)
    Cotizacion updateModel(UpdateCotizacionDTO updDTO, @MappingTarget Cotizacion entity);
}
