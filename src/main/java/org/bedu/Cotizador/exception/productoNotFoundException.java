package org.bedu.Cotizador.exception;

public class productoNotFoundException extends runtimeException {
    public productoNotFoundException(long productoId) {
        super("ERR_DATA_NOT_FOUND", "No se encontró el producto especificado", productoId);
    }
}
