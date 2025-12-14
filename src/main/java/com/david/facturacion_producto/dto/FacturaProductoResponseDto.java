package com.david.facturacion_producto.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FacturaProductoResponseDto(UUID idFacturaProducto, UUID productoId, String nombreProducto,
                                         Integer cantidadProducto, BigDecimal precioUnitarioProducto,
                                         BigDecimal subtotalProducto) {
}
