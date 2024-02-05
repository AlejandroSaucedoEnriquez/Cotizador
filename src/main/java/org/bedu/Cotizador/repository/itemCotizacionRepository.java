package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.itemCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface itemCotizacionRepository extends JpaRepository<itemCotizacion, Long> {
}
