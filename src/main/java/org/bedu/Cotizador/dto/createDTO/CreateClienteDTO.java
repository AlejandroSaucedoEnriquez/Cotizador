package org.bedu.Cotizador.dto.createDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateClienteDTO {
    @Schema(description = "Nombre del cliente", example = "Rodrigo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar en blanco")
    private String apellido;
    @NotBlank(message = "La dirección no puede estar en blanco")
    private String direccion;
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;
    @NotBlank(message = "El teléfono no puede estar en blanco")
    @Size(min = 10, max = 10, message = "El teléfono debe tener exactamente 10 caracteres")
    private String telefono;
}
