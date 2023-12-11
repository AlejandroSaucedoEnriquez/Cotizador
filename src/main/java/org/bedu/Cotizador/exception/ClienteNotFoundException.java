package org.bedu.Cotizador.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(long clienteId) {
        super("ERR_DATA_NOT_FOUND", "No se encontró el cliente especificado", clienteId);
    }
}
