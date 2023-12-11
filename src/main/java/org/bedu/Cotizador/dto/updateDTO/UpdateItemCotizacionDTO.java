package org.bedu.Cotizador.dto.updateDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateItemCotizacionDTO {
    private Long productoId;
    private int cantidad;
}