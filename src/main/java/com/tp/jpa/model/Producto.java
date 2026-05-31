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
@EqualsAndHashCode(callSuper = true, of = {"id", "nombre"})
@Entity
@Table(name = "productos")
public class Producto extends Base {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Double precio;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private int stock;

    @Column(length = 255)
    private String imagen;

    @Builder.Default
    @Column(name = "disponible", nullable = false)
    private Boolean disponible = true;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}