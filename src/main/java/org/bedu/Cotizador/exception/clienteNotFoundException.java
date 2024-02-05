package org.bedu.Cotizador.exception;

public class clienteNotFoundException extends runtimeException {

    public clienteNotFoundException(long clienteId) {
        super("ERR_DATA_NOT_FOUND", "No se encontró el cliente especificado", clienteId);
    }
}
