package com.david.facturacion_producto.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Billing API",
                version = "1.0.0",
                description = "REST API for managing clients, invoices and invoice line items"
        )
)
public class OpenApiConfig {}
