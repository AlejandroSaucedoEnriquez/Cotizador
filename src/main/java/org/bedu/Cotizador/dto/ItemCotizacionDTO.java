package org.bedu.Cotizador.dto;

import lombok.Data;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ItemCotizacionDTO {
    @Schema(description = "Identificador de Cotizacion", example = "5")
    private Long id;
    @Schema(description = "Productos para Cotizacion", example = "Caminadora Life fitness Signature")
    private ProductoDTO producto;
    @Schema(description = "Cantidad de productos para la Cotizacion", example = "2")
    private int cantidad;
    @Schema(description = "Precio del Producto de Cotizacion", example = "15000")
    private BigDecimal precioUnitario;
    @Schema(description = "Subtotal de la Cotizacion", example = "15000")
    private BigDecimal subtotal;
}
