package com.david.facturacion_producto.repository;

import com.david.facturacion_producto.model.Factura;
import com.david.facturacion_producto.model.FacturaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FacturaProductoRepository extends JpaRepository<FacturaProducto, UUID> {
    List<FacturaProducto> findByFacturaId(UUID facturaId);


    //Para calcular la suma de subtotales, sin incurrir en un N+1
    @Query("""
    SELECT fp.factura.id, SUM(fp.subtotal)
    FROM FacturaProducto fp
    GROUP BY fp.factura.id
""")
    List<Object[]> obtenerTotalesPorFactura();

}
