package com.david.facturacion_producto.service;

import com.david.facturacion_producto.dto.FacturaRequestDto;
import com.david.facturacion_producto.dto.FacturaResponseDto;
import com.david.facturacion_producto.dto.FacturaResumenResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FacturaService {


    List<FacturaResumenResponseDto> listarFacturas();

    FacturaResumenResponseDto crearFactura(FacturaRequestDto facturaRequestDto);

    FacturaResumenResponseDto modificarParcialFactura(UUID id, FacturaRequestDto facturaRequestDto);

    FacturaResponseDto eliminarFactura(UUID id);

    List<FacturaResumenResponseDto> encontrarFacturasEntreFechas(LocalDateTime inicio, LocalDateTime fin);

    List<FacturaResumenResponseDto> encontrarFacturaPorFecha(LocalDateTime fecha);

    FacturaResponseDto obtenerFacturaCompleta(UUID id);
}
