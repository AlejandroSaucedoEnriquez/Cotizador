package org.bedu.Cotizador.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClienteDTO {
    @Schema(description = "Identificador del cliente", example = "20")
    private long id;
    @Schema(description = "Nombre del cliente", example = "Rodrigo")
    private String nombre;
    @Schema(description = "apellido del cliente", example = "Chavez Ramos")
    private String apellido;
    @Schema(description = "direccion del cliente", example = "Av. Vallarta 1532")
    private String direccion;
    @Schema(description = "correo electronico del cliente", example = "ejemplo@gmail.com")
    private String email;
    @Schema(description = "telefono del cliente", example = "3315255110")
    private String telefono;
}
