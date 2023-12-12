package org.bedu.Cotizador.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FacturaDTO {
        
        private CotizacionDTO cotizacion;
        private BigDecimal total;
}
