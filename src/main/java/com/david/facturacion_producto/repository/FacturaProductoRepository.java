package com.david.facturacion_producto.repository;

import com.david.facturacion_producto.model.FacturaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FacturaProductoRepository extends JpaRepository<FacturaProducto, UUID> {
    List<FacturaProducto> findByFacturaId(UUID facturaId);
}
