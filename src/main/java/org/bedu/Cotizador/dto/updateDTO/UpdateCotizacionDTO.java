package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;


import java.util.List;

@Data
public class UpdateCotizacionDTO {
    private Long clienteId;
    private List<UpdateItemCotizacionDTO> items;
}
