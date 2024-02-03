package org.bedu.Cotizador.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "items_cotizacion")
public class ItemCotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cotizacion_id", nullable = false)
    private Cotizacion cotizacion;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private BigDecimal precioUnitario;

    @Column
    private BigDecimal subtotal;

    @Override
    public String toString() {
        return "ItemCotizacion{" +
                "id=" + id +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}