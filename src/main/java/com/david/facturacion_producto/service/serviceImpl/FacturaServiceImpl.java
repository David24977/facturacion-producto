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
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    //Hacer la suma de los subtotales para mostrarlos en factura
    @Override
    public List<FacturaResumenResponseDto> listarFacturas() {

        // Traer todas las facturas (1 query)
        List<Factura> facturas = facturaRepository.findAll();

        // Traer todos los totales ya calculados (1 query)
        Map<UUID, BigDecimal> totalesPorFactura =
                facturaProductoRepository.obtenerTotalesPorFactura()
                        .stream()
                        .collect(Collectors.toMap(
                                fila -> (UUID) fila[0],
                                fila -> (BigDecimal) fila[1]
                        ));

        // Construir el DTO resumen
        return facturas.stream()
                .map(f -> new FacturaResumenResponseDto(
                        f.getId(),
                        f.getClienteNombre(),
                        f.getFecha(),
                        totalesPorFactura.getOrDefault(
                                f.getId(), BigDecimal.ZERO
                        )
                ))
                .toList();
    }


    @Override
    public FacturaResumenResponseDto crearFactura(FacturaRequestDto facturaRequestDto) {
        BigDecimal total = BigDecimal.ZERO;
        Factura factura = new Factura();
        factura.setClienteNombre(facturaRequestDto.getClienteNombre());
        facturaRepository.save(factura);
        return  new FacturaResumenResponseDto(
                factura.getId(),
                factura.getClienteNombre(),
                factura.getFecha(),
                total
                );
    }

    @Override
    public FacturaResumenResponseDto modificarParcialFactura(UUID id, FacturaRequestDto facturaRequestDto) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada"));


        if(facturaRequestDto.getClienteNombre() != null){
            factura.setClienteNombre(facturaRequestDto.getClienteNombre());
        }
        facturaRepository.save(factura);
        return new FacturaResumenResponseDto(
                factura.getId(),
                factura.getClienteNombre(),
                factura.getFecha(),
                facturaProductoRepository.findByFacturaId(id)
                        .stream()
                        .map(FacturaProducto::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

    }

    @Override
    public FacturaResponseDto eliminarFactura(UUID id) {
        Factura facturaEliminada = facturaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada"));

        facturaRepository.delete(facturaEliminada);
        List<FacturaProductoResponseDto> lineas =
                facturaProductoService.findByFacturaId(facturaEliminada.getId());

        BigDecimal total = lineas.stream()
                .map(FacturaProductoResponseDto::subtotalProducto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FacturaResponseDto(
                facturaEliminada.getId(),
                facturaEliminada.getClienteNombre(),
                facturaEliminada.getFecha(),
                total,
                lineas
        );

    }



    @Override
    public List<FacturaResumenResponseDto> encontrarFacturasEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        List<Factura> facturasFechas = facturaRepository.findFechaBetween(inicio, fin);

        // Traer todos los totales ya calculados (1 query)
        Map<UUID, BigDecimal> totalesPorFactura =
                facturaProductoRepository.obtenerTotalesPorFactura()
                        .stream()
                        .collect(Collectors.toMap(
                                fila -> (UUID) fila[0],
                                fila -> (BigDecimal) fila[1]
                        ));

        // Construir el DTO resumen
        return facturasFechas.stream()
                .map(f -> new FacturaResumenResponseDto(
                        f.getId(),
                        f.getClienteNombre(),
                        f.getFecha(),
                        totalesPorFactura.getOrDefault(
                                f.getId(), BigDecimal.ZERO
                        )
                ))
                .toList();
    }

    @Override
    public List<FacturaResumenResponseDto> encontrarFacturaPorFecha(LocalDateTime fecha) {
        List<Factura> facturaPorFecha = facturaRepository.findFecha(fecha);
        Map<UUID, BigDecimal> totalesPorFactura =
                facturaProductoRepository.obtenerTotalesPorFactura()
                        .stream()
                        .collect(Collectors.toMap(
                                fila -> (UUID) fila[0],
                                fila -> (BigDecimal) fila[1]
                        ));

        // Construir el DTO resumen
        return facturaPorFecha.stream()
                .map(f -> new FacturaResumenResponseDto(
                        f.getId(),
                        f.getClienteNombre(),
                        f.getFecha(),
                        totalesPorFactura.getOrDefault(
                                f.getId(), BigDecimal.ZERO
                        )
                ))
                .toList();
    }

    @Override
    public FacturaResponseDto obtenerFacturaCompleta(UUID id){
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
