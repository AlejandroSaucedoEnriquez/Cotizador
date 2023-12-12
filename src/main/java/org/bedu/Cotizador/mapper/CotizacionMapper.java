package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.ProductoDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateItemCotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.bedu.Cotizador.model.Producto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CotizacionMapper {

    @Mapping(target = "clienteId", source = "cliente.id")
    CotizacionDTO toDTO (Cotizacion entity);


}
