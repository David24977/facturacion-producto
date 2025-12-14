package com.david.facturacion_producto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacturaProductoRequestDto {
    @NotNull
    private UUID facturaId;
    @NotNull
    private UUID productoId;
    @NotNull
    private Integer cantidadProducto;



}
