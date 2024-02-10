package org.bedu.Cotizador.componet;

import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CotizacionComponent {
    private final CotizacionService cotizacionService;

    @Autowired
    public CotizacionComponent(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    public Cotizacion getCotizacion (Long cotizacionId){
        return cotizacionService.findById(cotizacionId);
    }

    public Cotizacion saveCotizacion(Cotizacion cotizacion) {
        return cotizacionService.saveCotizacionEntity(cotizacion);
    }
}
