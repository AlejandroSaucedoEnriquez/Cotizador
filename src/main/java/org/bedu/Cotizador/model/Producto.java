package org.bedu.Cotizador.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String sku;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;
}
