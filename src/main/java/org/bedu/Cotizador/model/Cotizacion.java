package org.bedu.Cotizador.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cotizaciones")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Producto producto;
    @ManyToOne(cascade = CascadeType.ALL)
    private Factura factura;
    @ManyToOne(cascade = CascadeType.ALL)
    private Cliente cliente;
    @Column(nullable = false)
    private double total;
}
