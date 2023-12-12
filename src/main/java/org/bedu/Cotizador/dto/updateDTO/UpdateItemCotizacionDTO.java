package org.bedu.Cotizador.dto.updateDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateItemCotizacionDTO {
    private Long productoId;
    private int cantidad;
}