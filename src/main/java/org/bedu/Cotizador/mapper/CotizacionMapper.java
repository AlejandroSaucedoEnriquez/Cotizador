package org.bedu.Cotizador.mapper;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.dto.updateDTO.UpdateItemCotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.model.ItemCotizacion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {ClienteMapper.class, ProductoMapper.class})
public interface CotizacionMapper {

    CotizacionMapper INSTANCE = Mappers.getMapper(CotizacionMapper.class);

    @Mapping(target = "clienteId", ignore = true)
    @Mapping(target = "id", ignore = true)
    CotizacionDTO toDTO(Cotizacion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    Cotizacion toModel(CreateCotizacionDTO dto);

    @Mapping(target = "productoId", ignore = true)
    ItemCotizacionDTO toDTO(ItemCotizacion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "cotizacion", ignore = true)
    @Mapping(target = "producto", source = "productoId", qualifiedByName = "toModelProducto")
    ItemCotizacion toModel(CreateItemCotizacionDTO data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "cotizacion", ignore = true)
    @Mapping(target = "producto", source = "productoId", qualifiedByName = "toModelProducto") // Utiliza el nuevo método
    ItemCotizacion toModel(CreateItemCotizacionDTO data, @MappingTarget ItemCotizacion target);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "toModel")
    Cotizacion toCotizacion(CreateCotizacionDTO dto);

    List<ItemCotizacion> toItems(List<CreateItemCotizacionDTO> items);

}
