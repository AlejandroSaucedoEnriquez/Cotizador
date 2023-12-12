package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class CreateProductoDTO {
    @Schema(description = "Nombre del producto", example = "Caminadora Life fitness Signature")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String nombre;
    @Schema(description = "Sku del producto", example = "01cams")
    private String sku;
    @Schema(description = "Precio del producto", example = "15000")
    @NotNull
    @NotNull(message = "El precio no puede estar en blanco")
    @Digits(integer = 200, fraction = 2, message = "El precio debe tener hasta dos dígitos decimales")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;
    @Schema(description = "Stock del producto", example = "10")
    @Positive (message = "El stock debe ser un número positivo")
    private int stock;
    @Schema(description = "Descripcion del producto", example = "Caminadora para hacer ejercicio cardiovascular")
    @Size (max = 150, message = "La descripción debe tener como máximo 150 caracteres")
    @NotBlank(message = "La descripción no puede estar en blanco")
    private String descripcion;
    @Schema(description = "Categoria del producto", example = "Cardio")
    @NotBlank(message = "La categoría no puede estar en blanco")
    private String categoria;
    @Schema(description = "Marca del producto", example = "Life fitness")
    @NotBlank(message = "La marca no puede estar en blanco")
    private String marca;
    @Schema(description = "Modelo del producto", example = "Signature")
    @NotBlank(message = "El modelo no puede estar en blanco")
    private String modelo;
}
