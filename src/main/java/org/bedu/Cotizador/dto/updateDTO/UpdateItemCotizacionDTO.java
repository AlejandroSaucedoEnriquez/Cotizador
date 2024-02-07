package org.bedu.Cotizador.dto.updateDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;



@Data
@AllArgsConstructor
public class UpdateItemCotizacionDTO {
    @Schema(description = "Identificador del Producto para sustituir la Cotizacion", example = "30")
    private Long productoId;
    @Schema(description = "Cantidad de productos para sustituir la Cotizacion", example = "3")
    private int cantidad;
}