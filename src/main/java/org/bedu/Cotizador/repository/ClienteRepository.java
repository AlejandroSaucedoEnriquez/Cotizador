package org.bedu.Cotizador.repository;

import org.bedu.Cotizador.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
