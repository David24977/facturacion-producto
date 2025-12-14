package com.david.facturacion_producto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        name = "factura_producto",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"factura_id", "producto_id"})
        }
)
public class FacturaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    //Muchas lineas pertenecen a una factura
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @PrePersist
    @PreUpdate
    private void calcularSubTotal(){
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
