package com.david.facturacion_producto.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductoResponseDto(UUID idProducto, String nombreProducto, BigDecimal precioProducto) {
}
