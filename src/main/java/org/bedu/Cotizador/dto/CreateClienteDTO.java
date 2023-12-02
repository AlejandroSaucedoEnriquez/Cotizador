package org.bedu.Cotizador.dto;

import lombok.Data;

@Data
public class CreateClienteDTO {
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private String telefono;
}
