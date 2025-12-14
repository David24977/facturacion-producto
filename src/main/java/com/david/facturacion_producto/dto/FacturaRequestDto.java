package com.david.facturacion_producto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacturaRequestDto {
    @NotBlank
    @Size(min = 3, max = 40)
    private String clienteNombre;
}
