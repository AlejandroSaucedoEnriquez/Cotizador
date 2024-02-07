package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UpdateProductoDTO {
    @Schema(description = "Nombre del Producto", example = "Caminadora Precor P80")
    private String nombre;
    @Schema(description = "Sku del Producto", example = "CamPla001")
    private String sku;
    @Schema(description = "Precio del Producto", example = "20000")
    private BigDecimal precio;
    @Schema(description = "Stock del Producto", example = "20")
    private int stock;
    @Schema(description = "Descripcion del Producto", example = "Caminadora para hacer ejercicio cardiovascular")
    private String descripcion;
    @Schema(description = "Categoria del Producto", example = "Cardio")
    private String categoria;
     @Schema(description = "Marca del Producto", example = "Precor")
    private String marca;
    @Schema(description = "Modelo del Producto", example = "P80")
    private String modelo;
}