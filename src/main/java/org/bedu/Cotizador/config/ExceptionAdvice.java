package org.bedu.Cotizador.config;

import org.bedu.Cotizador.dto.errorDTO;
import org.bedu.Cotizador.exception.runtimeException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public errorDTO validationError(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream().map(x -> x.getDefaultMessage()).toList();
        return new errorDTO("ERR_VALID", "Hubo un error al procesar los datos de entrada", errors);
    }

    @ExceptionHandler(runtimeException.class)
    public errorDTO applicationError(runtimeException ex) {
        return new errorDTO(ex.getCode(), ex.getMessage(), ex.getDetails());
    }

    @ExceptionHandler(Exception.class)
    public errorDTO unknownError(Exception ex) {
        log.error(ex.getMessage());
        return new errorDTO("ERR_EXC", "Ocurrió un error inesperado", "No existen datos cliente o producto");
    }
}
