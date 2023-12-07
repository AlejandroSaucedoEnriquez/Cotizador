package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.ItemCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCotizacionRepository extends JpaRepository<ItemCotizacion, Long> {
}
