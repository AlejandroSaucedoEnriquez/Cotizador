package org.bedu.Cotizador.repository;

import java.util.List;
import org.bedu.Cotizador.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
