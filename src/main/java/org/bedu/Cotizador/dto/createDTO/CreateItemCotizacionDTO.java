package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bedu.Cotizador.model.Cotizacion;


import java.util.List;

@Data
@AllArgsConstructor
public class CreateItemCotizacionDTO {
    @NotNull(message = "El producto no puede ser nulo")
    private Long productoId;
    private int cantidad;
}

