package org.bedu.Cotizador.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(long productoId) {
        super("ERR_DATA_NOT_FOUND", "No se encontró el producto especificado", productoId);
    }
}
