package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaService extends JpaRepository<Factura, Long> {
}
