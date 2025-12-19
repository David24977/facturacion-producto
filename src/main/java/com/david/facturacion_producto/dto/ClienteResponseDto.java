package com.david.facturacion_producto.dto;

import java.util.UUID;

public record ClienteResponseDto(UUID idCliente, String NombreCliente, String dni, String email) {
}
