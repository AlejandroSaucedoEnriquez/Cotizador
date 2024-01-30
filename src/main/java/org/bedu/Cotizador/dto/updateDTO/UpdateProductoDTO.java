package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UpdateProductoDTO {
    @Schema(description = "Nombre del producto", example = "Caminadora Precor P80")
    private String nombre;
    @Schema(description = "Sku del producto", example = "01Camp80")
    private String sku;
    @Schema(description = "Precio del producto", example = "20000")
    private BigDecimal precio;
    @Schema(description = "Stock del producto", example = "20")
    private int stock;
    @Schema(description = "Descripcion del producto", example = "Caminadora para hacer ejercicio cardiovascular")
    private String descripcion;
    @Schema(description = "Categoria del producto", example = "Cardio")
    private String categoria;
     @Schema(description = "Marca del producto", example = "Precor")
    private String marca;
    @Schema(description = "Modelo del producto", example = "P80")
    private String modelo;
}