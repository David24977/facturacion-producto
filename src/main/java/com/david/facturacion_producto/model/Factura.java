package com.david.facturacion_producto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private LocalDateTime fecha;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)//NO Queremos facturas sin clientes(optional=false)
    @JoinColumn(name = "factura_cliente", nullable = false)//La FK no puede ser null(nullable=false)
    private Cliente cliente;
    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal iva;

    @PrePersist //Para que se ponga la fecha automáticamente al crear la entidad
    public void prePersist() {

        this.fecha = LocalDateTime.now();
    }


}
