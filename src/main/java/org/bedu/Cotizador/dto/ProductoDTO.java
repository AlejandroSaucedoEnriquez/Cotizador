package org.bedu.Cotizador.dto;
  
     
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductoDTO {
    private int id;
    private String nombre;
    private String sku;
    private BigDecimal precio;
    private int stock;
    private String descripcion;
    private String categoria;
    private String marca;
    private String modelo; 
}
