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
public class cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private org.bedu.Cotizador.model.cliente cliente;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<itemCotizacion> items;

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