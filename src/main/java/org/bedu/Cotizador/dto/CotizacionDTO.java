package org.bedu.Cotizador.dto;


import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class CotizacionDTO {
    @Schema(description = "Identificador de Cotizacion", example = "5")
    private Long id;
    @Schema(description = "Identificador del cliente", example = "20")
    private Long clienteId;
    private List<ItemCotizacionDTO> items;
    @Schema(description = "Fecha de cotizacion", example = "2023-12-10")
    private LocalDate fecha;
    @Schema(description = "Subtotal del pedido del cliente", example = "35000")
    private BigDecimal total;
}