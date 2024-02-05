package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Data
public class updateCotizacionDTO {
    @Schema(description = "Identificador del cliente para sustituir la cotizacion", example = "5")
    private Long clienteId;
    private List<updateItemCotizacionDTO> items;
}