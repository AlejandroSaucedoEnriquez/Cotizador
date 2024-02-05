package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productoRepository extends JpaRepository<producto, Long> {
}
