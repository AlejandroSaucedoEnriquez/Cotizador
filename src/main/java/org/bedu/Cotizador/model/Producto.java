package org.bedu.Cotizador.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String nombre;
    @Column
    private String sku;
    @Column(nullable = false)
    @NumberFormat(pattern = "#0.00")
    private BigDecimal precio;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false, length = 150)
    private String descripcion;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private String modelo;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCotizacion> items = new ArrayList<>();
}
