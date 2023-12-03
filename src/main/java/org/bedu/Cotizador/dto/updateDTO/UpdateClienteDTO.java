package org.bedu.Cotizador.dto.updateDTO;

import lombok.Data;

@Data
public class UpdateClienteDTO {
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private String telefono;
}
