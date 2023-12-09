package org.bedu.Cotizador.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@Table(name = "items_cotizacion")
public class ItemCotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "cotizacion_id")
    private Cotizacion cotizacion;

    @Column(nullable = false)
    private int cantidad;

    @Column
    private BigDecimal subtotal; // Subtotal de este ítem
}