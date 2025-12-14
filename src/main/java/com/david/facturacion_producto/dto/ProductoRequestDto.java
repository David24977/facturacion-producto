package com.david.facturacion_producto.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoRequestDto {

    @NotBlank
    @Size(min = 3, max = 30)
    private String nombreProducto;
    @NotNull
    @Digits(integer = 8, fraction = 2)
    @DecimalMin("0.01")
    private BigDecimal precioProducto;
}
