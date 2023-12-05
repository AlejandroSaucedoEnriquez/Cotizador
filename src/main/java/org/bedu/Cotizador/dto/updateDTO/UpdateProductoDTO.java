package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;

@Data
public class UpdateProductoDTO {
    private String nombre;
    private String sku;
    private double precio;
    private int stock;
    private String descripcion;
    private String categoria;
    private String marca;
    private String modelo;
}
