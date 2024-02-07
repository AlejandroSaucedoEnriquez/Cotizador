package org.bedu.Cotizador.exception;

public class ProductoNotFoundException extends RntimeException {
    public ProductoNotFoundException(long productoId) {
        super("ERR_DATA_NOT_FOUND", "No se encontró el Producto especificado", productoId);
    }
}
