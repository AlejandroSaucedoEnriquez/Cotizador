package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Cotizacion;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateFacturaDTO {
    private Cliente cliente;
    private List<Cotizacion> cotizaciones;
    private LocalDate fechaFacture;
}
