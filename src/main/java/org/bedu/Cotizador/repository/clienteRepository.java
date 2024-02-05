package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clienteRepository extends JpaRepository<cliente, Long> {
}