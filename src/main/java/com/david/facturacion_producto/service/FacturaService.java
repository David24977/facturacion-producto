package com.david.facturacion_producto.service;

import com.david.facturacion_producto.dto.FacturaRequestDto;
import com.david.facturacion_producto.dto.FacturaResponseDto;
import com.david.facturacion_producto.dto.FacturaResumenResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FacturaService {


    List<FacturaResumenResponseDto> listarFacturas();

    FacturaResumenResponseDto crearFactura(FacturaRequestDto facturaRequestDto);

    FacturaResponseDto eliminarFactura(UUID id);

    List<FacturaResumenResponseDto> encontrarFacturasEntreFechas(LocalDate inicio, LocalDate fin);

    List<FacturaResumenResponseDto> encontrarFacturaPorFecha(LocalDate inicio, LocalDate fin);

    FacturaResponseDto obtenerFacturaCompleta(UUID id);
}
