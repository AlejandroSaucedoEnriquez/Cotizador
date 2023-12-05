package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Producto;
@Data
public class UpdateCotizacionDTO {
    private Producto producto;
    private Cliente cliente;
    private double total;
}
