package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Producto;

import java.util.List;

@Data
public class CreateCotizacionDTO {
    @NotNull
    private Long clienteId;
    private List<CreateItemCotizacionDTO> items;
}
