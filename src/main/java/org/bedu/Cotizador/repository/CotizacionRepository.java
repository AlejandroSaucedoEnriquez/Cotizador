package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
}
