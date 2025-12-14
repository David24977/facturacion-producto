package com.david.facturacion_producto.dto;

import com.david.facturacion_producto.model.FacturaProducto;
import java.util.List;
import java.util.UUID;

public record FacturaResponseDto(UUID idFactura, String clienteNombre, List<FacturaProductoResponseDto> lineas) {
}
