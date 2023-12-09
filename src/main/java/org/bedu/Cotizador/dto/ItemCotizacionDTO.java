package org.bedu.Cotizador.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemCotizacionDTO {
    private long id;
    private long productoId;
    private int cantidad;
    private BigDecimal subtotal;
}
