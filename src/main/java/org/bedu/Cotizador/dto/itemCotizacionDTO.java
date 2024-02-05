package org.bedu.Cotizador.dto;

import lombok.Data;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class itemCotizacionDTO {
    @Schema(description = "Identificador de cotizacion", example = "5")
    private Long id;
    @Schema(description = "Productos para cotizacion", example = "Caminadora Life fitness Signature")
    private productoDTO producto;
    @Schema(description = "Cantidad de productos para la cotizacion", example = "2")
    private int cantidad;
    @Schema(description = "Precio del producto de cotizacion", example = "15000")
    private BigDecimal precioUnitario;
    @Schema(description = "Subtotal de la cotizacion", example = "15000")
    private BigDecimal subtotal;
}
