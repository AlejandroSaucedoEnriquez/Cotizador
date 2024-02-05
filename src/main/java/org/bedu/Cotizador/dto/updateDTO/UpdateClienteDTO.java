package org.bedu.Cotizador.dto.updateDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateClienteDTO {
    @Schema(description = "Nombre del cliente", example = "Armando")
    private String nombre;
    @Schema(description = "Apellido del cliente", example = "Gonzalez Rivera")
    private String apellido;
    @Schema(description = "Direccion del cliente", example = "Avenida Calzada Independencia #12")
    private String direccion;
    @Schema(description = "Correo electronico del cliente", example = "ejemplo@hotmail.com")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;
    @Schema(description = "Telefono del cliente", example = "5512478910")
    @Size(min = 10, max = 10, message = "El teléfono debe tener exactamente 10 caracteres")
    private String telefono;
}
