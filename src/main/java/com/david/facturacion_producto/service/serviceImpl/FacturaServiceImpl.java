package com.david.facturacion_producto.service.serviceImpl;

import com.david.facturacion_producto.dto.FacturaProductoResponseDto;
import com.david.facturacion_producto.dto.FacturaRequestDto;
import com.david.facturacion_producto.dto.FacturaResponseDto;
import com.david.facturacion_producto.dto.FacturaResumenResponseDto;
import com.david.facturacion_producto.model.Factura;
import com.david.facturacion_producto.model.FacturaProducto;
import com.david.facturacion_producto.repository.FacturaProductoRepository;
import com.david.facturacion_producto.repository.FacturaRepository;
import com.david.facturacion_producto.service.FacturaProductoService;
import com.david.facturacion_producto.service.FacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FacturaServiceImpl implements FacturaService {
    private final FacturaRepository facturaRepository;
    private final FacturaProductoService facturaProductoService;
    private final FacturaProductoRepository facturaProductoRepository;


    public FacturaServiceImpl(FacturaRepository facturaRepository, FacturaProductoService facturaProductoService, FacturaProductoRepository facturaProductoRepository) {
        this.facturaRepository = facturaRepository;
        this.facturaProductoService = facturaProductoService;
        this.facturaProductoRepository = facturaProductoRepository;
    }

    @Override
    public List<FacturaResumenResponseDto> listarFacturas() {

        return facturaRepository.findAll()
                .stream()
                .map(f -> new FacturaResumenResponseDto(
                        f.getId(),
                        f.getClienteNombre(),
                        f.getFecha(),
                        facturaProductoRepository.findByFacturaId(f.getId())
                                .stream().map(n -> n.getSubtotal())
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ))
                .toList();
    }

    @Override
    public FacturaResponseDto crearFactura(FacturaRequestDto facturaRequestDto) {
        return null;
    }

    @Override
    public FacturaResponseDto modificarParcialFactura(UUID id, FacturaRequestDto facturaRequestDto) {
        return null;
    }

    @Override
    public FacturaResponseDto eliminarFactura(UUID id) {
        return null;
    }

    @Override
    public List<FacturaResponseDto> encontrarFacturasEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        return List.of();
    }

    @Override
    public List<FacturaResponseDto> encontrarFacturaPorFecha(LocalDateTime fecha) {
        return List.of();
    }

    public FacturaResponseDto obtenerFactura(UUID id){
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Factura no encontrada"));
        List<FacturaProductoResponseDto> lineas =
                facturaProductoService.findByFacturaId(factura.getId());

        BigDecimal total = lineas.stream()
                .map(FacturaProductoResponseDto::subtotalProducto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FacturaResponseDto(
                factura.getId(),
                factura.getClienteNombre(),
                factura.getFecha(),
                total,
                lineas
        );

    }





}
