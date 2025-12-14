package com.david.facturacion_producto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FacturaResumenResponseDto(UUID id, String nombreCliente, LocalDateTime fecha, BigDecimal total) {
}
