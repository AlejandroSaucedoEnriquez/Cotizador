package org.bedu.Cotizador.exception;

public class ClienteNotFoundException extends RntimeException {

    public ClienteNotFoundException(long clienteId) {
        super("ERR_DATA_NOT_FOUND", "No se encontró el Cliente especificado", clienteId);
    }
}
