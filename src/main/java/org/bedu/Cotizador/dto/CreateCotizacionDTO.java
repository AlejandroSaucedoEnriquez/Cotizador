package org.bedu.Cotizador.dto;

import lombok.Data;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Producto;
@Data
public class CreateCotizacionDTO {
    /*private Producto producto;
    private Cliente cliente;*/
    private double total;
}