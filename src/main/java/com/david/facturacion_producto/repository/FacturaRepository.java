package com.david.facturacion_producto.repository;

import com.david.facturacion_producto.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FacturaRepository extends JpaRepository<Factura, UUID> {

    List<Factura> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}


