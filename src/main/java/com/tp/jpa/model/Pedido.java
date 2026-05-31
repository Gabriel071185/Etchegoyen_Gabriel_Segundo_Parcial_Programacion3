package com.tp.jpa.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.enums.FormaPago;
import com.tp.jpa.interfaces.Calculable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = true, of = {"id"})
@Entity
@Table(name = "pedidos")
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Double total;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id")  // FK en la tabla detalles_pedido
    private Set<DetallePedido> detallePedidos = new HashSet<>();

    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido d = findDetallePedidoByProducto(producto);
        if (d == null) {
            DetallePedido detalle = new DetallePedido(cantidad, producto);
            detallePedidos.add(detalle);
        } else {
            d.incrementCantidad(cantidad);
        }
        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido d : detallePedidos) {
            if (d.getProducto().equals(producto)) return d;
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        detallePedidos.removeIf(d -> d.getProducto().equals(producto));
        calcularTotal();
    }

    public void calcularTotal() {
        this.total = detallePedidos.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }
}