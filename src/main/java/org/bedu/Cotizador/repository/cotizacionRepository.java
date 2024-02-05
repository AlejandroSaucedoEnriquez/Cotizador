package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cotizacionRepository extends JpaRepository<cotizacion, Long> {
}
