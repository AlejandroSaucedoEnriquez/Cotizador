package org.bedu.Cotizador.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemCotizacionDTO {
    private Long id;
    private Long productoId;
    private int cantidad;
    private BigDecimal subtotal;
}
