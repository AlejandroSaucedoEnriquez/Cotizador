package org.bedu.Cotizador.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cotizaciones")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCotizacion> items;

    @Column(nullable = false)
    private LocalDate fecha; // Fecha de la cotización

    @Column
    private BigDecimal total; // Subtotal de la cotización

    @PrePersist
    protected void onCreate() {
        // Establecer la fecha actual antes de persistir la entidad
        fecha = LocalDate.now();
    }

}
