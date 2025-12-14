package com.david.facturacion_producto.service.serviceImpl;

import com.david.facturacion_producto.dto.FacturaProductoRequestDto;
import com.david.facturacion_producto.dto.FacturaProductoResponseDto;
import com.david.facturacion_producto.model.FacturaProducto;
import com.david.facturacion_producto.repository.FacturaProductoRepository;
import com.david.facturacion_producto.service.FacturaProductoService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class FacturaProductoServiceImpl implements FacturaProductoService {
    private final FacturaProductoRepository facturaProductoRepository;

    public FacturaProductoServiceImpl(FacturaProductoRepository facturaProductoRepository) {
        this.facturaProductoRepository = facturaProductoRepository;
    }

    @Override
    public List<FacturaProductoResponseDto> findByFacturaId(UUID facturaId) {
        return List.of();
    }

    @Override
    public FacturaProductoResponseDto crearLinea(FacturaProductoRequestDto facturaProductoRequestDto) {
        return null;
    }

    @Override
    public FacturaProductoResponseDto modificarParcialLinea(UUID id, FacturaProductoRequestDto facturaProductoRequestDto) {
        return null;
    }

    @Override
    public FacturaProductoResponseDto eliminarLinea(UUID id) {
        return null;
    }

    public FacturaProductoResponseDto mapToResponseFacturaProducto(FacturaProducto facturaProducto){
                return new FacturaProductoResponseDto(
                        facturaProducto.getId(),
                        facturaProducto.getProducto().getId(),
                        facturaProducto.getProducto().getNombre(),
                        facturaProducto.getCantidad(),
                        facturaProducto.getPrecioUnitario(),
                        facturaProducto.getPrecioUnitario().multiply(BigDecimal.valueOf(facturaProducto.getCantidad()))
                );
    }
}
