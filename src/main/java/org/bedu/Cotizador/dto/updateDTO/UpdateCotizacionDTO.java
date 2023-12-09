package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;


import java.util.List;

@Data
public class UpdateCotizacionDTO {
    private List<CreateItemCotizacionDTO> itemsToAdd;
    private List<Long> itemsToDelete;
}
