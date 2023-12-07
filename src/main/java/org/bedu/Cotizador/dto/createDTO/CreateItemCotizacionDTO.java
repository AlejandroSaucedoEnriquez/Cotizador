package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateItemCotizacionDTO {
    @NotNull
    private Long productoId;

    @NotNull
    @Positive(message = "La cantidad debe ser un número positivo")
    private int cantidad;

    private List<CreateItemCotizacionDTO> itemsToAdd;
}
