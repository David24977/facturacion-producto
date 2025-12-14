package com.david.facturacion_producto.service;

import com.david.facturacion_producto.dto.FacturaProductoRequestDto;
import com.david.facturacion_producto.dto.FacturaProductoResponseDto;
import com.david.facturacion_producto.model.FacturaProducto;

import java.util.List;
import java.util.UUID;

public interface FacturaProductoService {

    List<FacturaProductoResponseDto> findByFacturaId(UUID facturaId);

    FacturaProductoResponseDto crearLinea(FacturaProductoRequestDto facturaProductoRequestDto);

    FacturaProductoResponseDto modificarParcialLinea(UUID id, FacturaProductoRequestDto facturaProductoRequestDto);

    FacturaProductoResponseDto eliminarLinea(UUID id);

    FacturaProductoResponseDto mapToResponseFacturaProducto(FacturaProducto facturaProducto);

}
