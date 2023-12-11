package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
public class CreateItemCotizacionDTO {
    @NotNull(message = "El producto no puede ser nulo")
    private Long productoId;
    private int cantidad;
}

