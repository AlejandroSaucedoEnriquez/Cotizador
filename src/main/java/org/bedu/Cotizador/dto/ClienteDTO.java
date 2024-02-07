package org.bedu.Cotizador.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClienteDTO {
    @Schema(description = "Identificador del Cliente", example = "20")
    private long id;
    @Schema(description = "Nombre del Cliente", example = "Rodrigo")
    private String nombre;
    @Schema(description = "Apellido del Cliente", example = "Chavez Ramos")
    private String apellido;
    @Schema(description = "Direccion del Cliente", example = "Avenida Vallarta #1532")
    private String direccion;
    @Schema(description = "Correo electronico del Cliente", example = "ejemplo@gmail.com")
    private String email;
    @Schema(description = "Telefono del Cliente", example = "3315255110")
    private String telefono;
}