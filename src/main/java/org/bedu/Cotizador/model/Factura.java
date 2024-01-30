package org.bedu.Cotizador.model;

import lombok.Data;

import java.math.BigDecimal;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.FacturaDTO;

@Data
public class Factura {
    private CotizacionDTO cotizacion;
    private BigDecimal total;

    public FacturaDTO toDTO() {
        FacturaDTO facturaDTO = new FacturaDTO();
        facturaDTO.setCotizacion(this.cotizacion);
        facturaDTO.setTotal(this.total);
        return facturaDTO;
    }
}
