package org.bedu.Cotizador.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class runtimeException extends Exception {
    private final String code;
    private final Object details;

    public runtimeException(String code, String message, Object details) {
        super(message);
        this.code = code;
        this.details = details;
    }
}
