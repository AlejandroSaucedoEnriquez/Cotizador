package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductoDTO {
    private String nombre;
    private String sku;
    private BigDecimal precio;
    private int stock;
    private String descripcion;
    private String categoria;
    private String marca;
    private String modelo;
}
