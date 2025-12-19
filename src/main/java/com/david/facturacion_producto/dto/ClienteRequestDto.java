package com.david.facturacion_producto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDto(
        @NotBlank
        @Size(min = 3, max = 40)
        String nombre,
        @NotBlank
        @Size(min = 9, max = 9)
        String dni,
        @NotBlank
        @Email
        @Size(min = 5, max = 50)
        String email) { }
