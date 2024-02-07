package org.bedu.Cotizador.dto;

import lombok.Data;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ProductoDTO {
    @Schema(description = "Identificador del Producto", example = "30")
    private int id;
    @Schema(description = "Nombre del Producto", example = "Caminadora Life fitness Signature")
    private String nombre;
    @Schema(description = "Sku del Producto", example = "01cams")
    private String sku;
    @Schema(description = "Precio del Producto", example = "15000")
    private BigDecimal precio;
    @Schema(description = "Stock del Producto", example = "10")
    private int stock;
    @Schema(description = "Descripcion del Producto", example = "Caminadora para hacer ejercicio cardiovascular")
    private String descripcion;
    @Schema(description = "Categoria del Producto", example = "Cardio")
    private String categoria;
    @Schema(description = "Marca del Producto", example = "Life fitness")
    private String marca;
    @Schema(description = "Modelo del Producto", example = "Signature")
    private String modelo; 
}
