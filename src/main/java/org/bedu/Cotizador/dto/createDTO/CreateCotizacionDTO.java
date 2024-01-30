package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCotizacionDTO {
    @Schema(description = "Identificador del cliente", example = "20")
    @NotNull(message = "El cliente no puede ser nulo")
    private Long clienteId;
    private List<CreateItemCotizacionDTO> items;
}