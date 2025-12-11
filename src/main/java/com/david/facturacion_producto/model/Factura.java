package com.david.facturacion_producto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false, length = 40)
    private String clienteNombre;

    @PrePersist //Para que se ponga la fecha automáticamente al crear la entidad
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }
}
