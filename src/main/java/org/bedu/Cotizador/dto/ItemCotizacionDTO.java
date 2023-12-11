package org.bedu.Cotizador.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemCotizacionDTO {
    private Long id;
    private ProductoDTO producto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
