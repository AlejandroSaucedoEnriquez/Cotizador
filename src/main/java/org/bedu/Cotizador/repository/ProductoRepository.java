package org.bedu.Cotizador.repository;

import java.util.List;

import org.bedu.Cotizador.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findAll();
}
