package org.bedu.Cotizador.model;

import lombok.Data;
import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.FacturaDTO;

import java.math.BigDecimal;

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
