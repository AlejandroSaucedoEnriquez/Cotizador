package org.bedu.Cotizador.dto.createDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bedu.Cotizador.model.Cliente;
import org.bedu.Cotizador.model.Producto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCotizacionDTO {
    @NotNull
    private Long clienteId;
    private List<CreateItemCotizacionDTO> items;
}
