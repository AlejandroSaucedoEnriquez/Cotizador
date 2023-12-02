package org.bedu.Cotizador.dto;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Producto;

@Data
public class CotizacionDTO {
    private long id;
    /*private Producto producto;
    private Cliente cliente;*/
    private double total;
}
