package com.david.facturacion_producto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 40)
    private String nombre;
    @Column(nullable = false, length = 9, unique = true)
    private String dni;
    @Column(nullable = false, length = 50)
    @Email
    private String email;
}
