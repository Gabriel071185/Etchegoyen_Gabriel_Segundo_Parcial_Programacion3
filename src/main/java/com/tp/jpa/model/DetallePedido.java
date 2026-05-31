package com.tp.jpa.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false, of = {"producto"})
@Entity
@Table(name = "detalles_pedido")
public class DetallePedido extends Base {

    @Column(nullable = false)
    private int cantidad;

    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calculateSubtotal();
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = calculateSubtotal();
    }

    public Double calculateSubtotal() {
        return this.cantidad * this.producto.getPrecio();
    }

    public void incrementCantidad(int incremento) {
        setCantidad(this.cantidad + incremento);
    }
}