package com.david.facturacion_producto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record FacturaResponseDto(UUID idFactura, String clienteNombre, String clienteEmail, LocalDateTime fecha, BigDecimal total, List<FacturaProductoResponseDto> lineas) {
}
