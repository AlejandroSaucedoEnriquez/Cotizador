package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateItemCotizacionDTO {
    @NotNull
    private Long productoId;
    @NotNull
    @Positive(message = "La cantidad debe ser un número positivo")
    private int cantidad;
}
