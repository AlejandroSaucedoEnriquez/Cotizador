package org.bedu.Cotizador.dto;

import lombok.Data;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ProductoDTO {
    @Schema(description = "Identificador del producto", example = "30")
    private int id;
    @Schema(description = "Nombre del producto", example = "Caminadora Life fitness Signature")
    private String nombre;
    @Schema(description = "Sku del producto", example = "01cams")
    private String sku;
    @Schema(description = "Precio del producto", example = "15000")
    private BigDecimal precio;
    @Schema(description = "Stock del producto", example = "10")
    private int stock;
    @Schema(description = "Descripcion del producto", example = "Caminadora para hacer ejercicio cardiovascular")
    private String descripcion;
    @Schema(description = "Categoria del producto", example = "Cardio")
    private String categoria;
    @Schema(description = "Marca del producto", example = "Life fitness")
    private String marca;
    @Schema(description = "Modelo del producto", example = "Signature")
    private String modelo; 
}
