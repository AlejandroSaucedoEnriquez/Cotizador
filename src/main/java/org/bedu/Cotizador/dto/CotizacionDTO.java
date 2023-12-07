package org.bedu.Cotizador.dto;


import lombok.Data;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Producto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CotizacionDTO {
    private Long id;
    private Long clienteId;
    private List<ItemCotizacionDTO> items;
    private LocalDate fecha;
    private BigDecimal subtotal;

}
