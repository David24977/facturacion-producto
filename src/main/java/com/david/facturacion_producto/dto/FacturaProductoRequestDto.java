package com.david.facturacion_producto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @Positive
    private Integer cantidadProducto;



}
