package org.bedu.Cotizador.dto.updateDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateClienteDTO {
    private String nombre;
    private String apellido;
    private String direccion;
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;
    @Size(min = 10, max = 10, message = "El teléfono debe tener exactamente 10 caracteres")
    private String telefono;
}
