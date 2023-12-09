package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCotizacionDTO {
    @NotNull
    private long clienteId;
    private List<CreateItemCotizacionDTO> items;
}
