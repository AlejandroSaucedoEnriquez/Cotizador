package org.bedu.Cotizador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class errorDTO {
    private String code;
    private String message;
    private Object details;
}
