package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
