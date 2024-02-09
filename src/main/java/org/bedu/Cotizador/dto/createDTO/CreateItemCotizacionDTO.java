package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemCotizacionDTO {
    @Schema(description = "Identificador del Producto para la Cotizacion", example = "5")
    @NotNull(message = "El Producto no puede ser nulo")
    private Long productoId;
    @Schema(description = "Cantidad de productos para la Cotizacion", example = "2")
    private int cantidad;

}