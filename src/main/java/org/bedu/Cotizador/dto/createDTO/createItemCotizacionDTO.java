package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createItemCotizacionDTO {
    @Schema(description = "Identificador del producto para la cotizacion", example = "5")
    @NotNull(message = "El producto no puede ser nulo")
    private Long productoId;
    @Schema(description = "Cantidad de productos para la cotizacion", example = "2")
    private int cantidad;
}