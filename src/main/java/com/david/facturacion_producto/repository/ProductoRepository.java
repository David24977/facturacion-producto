package com.david.facturacion_producto.repository;


import com.david.facturacion_producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    Optional<Producto> findByNombre(String nombre);
}
