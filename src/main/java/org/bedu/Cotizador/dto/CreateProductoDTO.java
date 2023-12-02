package org.bedu.Cotizador.dto;

import lombok.Data;

@Data
public class CreateProductoDTO {
    private String nombre;
    private String sku;
    private double precio;
    private int stock;
    private String descripcion;
    private String categoria;
    private String marca;
    private String modelo;
}