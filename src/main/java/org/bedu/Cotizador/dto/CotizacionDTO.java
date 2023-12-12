package org.bedu.Cotizador.dto;


import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CotizacionDTO {
    private Long id;
    private Long clienteId;
    private List<ItemCotizacionDTO> items;
    private LocalDate fecha;
    private BigDecimal total;
}
