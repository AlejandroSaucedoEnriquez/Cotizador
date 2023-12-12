package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductoDTO {
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String nombre;

    private String sku;

    @NotNull(message = "El precio no puede estar en blanco")
    @Digits(integer = 200, fraction = 2, message = "El precio debe tener hasta dos dígitos decimales")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @Positive (message = "El stock debe ser un número positivo")
    private int stock;

    @Size (max = 150, message = "La descripción debe tener como máximo 150 caracteres")
    @NotBlank(message = "La descripción no puede estar en blanco")
    private String descripcion;

    @NotBlank(message = "La categoría no puede estar en blanco")
    private String categoria;
    @NotBlank(message = "La marca no puede estar en blanco")
    private String marca;
    @NotBlank(message = "El modelo no puede estar en blanco")
    private String modelo;
}
