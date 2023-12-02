package org.bedu.Cotizador.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "cotizaciones")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Cliente cliente;

    private double total;
}
