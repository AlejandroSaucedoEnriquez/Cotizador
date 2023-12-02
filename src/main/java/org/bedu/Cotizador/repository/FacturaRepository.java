package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
