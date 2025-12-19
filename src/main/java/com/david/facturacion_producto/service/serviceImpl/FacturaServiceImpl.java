package com.david.facturacion_producto.service.serviceImpl;

import com.david.facturacion_producto.dto.FacturaProductoResponseDto;
import com.david.facturacion_producto.dto.FacturaRequestDto;
import com.david.facturacion_producto.dto.FacturaResponseDto;
import com.david.facturacion_producto.dto.FacturaResumenResponseDto;
import com.david.facturacion_producto.model.Cliente;
import com.david.facturacion_producto.model.Factura;
import com.david.facturacion_producto.repository.ClienteRepository;
import com.david.facturacion_producto.repository.FacturaProductoRepository;
import com.david.facturacion_producto.repository.FacturaRepository;
import com.david.facturacion_producto.service.FacturaProductoService;
import com.david.facturacion_producto.service.FacturaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class FacturaServiceImpl implements FacturaService {
    private final FacturaRepository facturaRepository;
    private final ClienteRepository clienteRepository;
    private final FacturaProductoRepository facturaProductoRepository;


    public FacturaServiceImpl(FacturaRepository facturaRepository, FacturaProductoService facturaProductoService, ClienteRepository clienteRepository, FacturaProductoRepository facturaProductoRepository) {
        this.facturaRepository = facturaRepository;
        this.clienteRepository = clienteRepository;
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
                .map(f -> {

                    BigDecimal base = totalesPorFactura.getOrDefault(
                            f.getId(), BigDecimal.ZERO
                    );

                    BigDecimal totalConIva = calcularTotalConIva(base, f.getIva());

                    return new FacturaResumenResponseDto(
                            f.getId(),
                            f.getCliente().getNombre(),
                            f.getCliente().getEmail(),
                            f.getFecha(),
                            totalConIva
                    );
                })
                .toList();

    }



    @Override
    public FacturaResumenResponseDto crearFactura(FacturaRequestDto facturaRequestDto) {

        Cliente cliente = clienteRepository.findById(facturaRequestDto.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setIva(facturaRequestDto.getIva());

        facturaRepository.save(factura);

        return new FacturaResumenResponseDto(
                factura.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                factura.getFecha(),
                BigDecimal.ZERO
        );
    }




    @Override
    public FacturaResponseDto eliminarFactura(UUID id) {
        Factura facturaEliminada = facturaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada"));

        facturaRepository.delete(facturaEliminada);
        // 2. Obtener las líneas (ENTIDADES) y mapearlas a DTO
        List<FacturaProductoResponseDto> lineas =
                facturaProductoRepository.findByFacturaId(facturaEliminada.getId())
                        .stream()
                        .map(fp -> new FacturaProductoResponseDto(
                                fp.getId(),
                                fp.getProducto().getId(),
                                fp.getProducto().getNombre(),
                                fp.getCantidad(),
                                fp.getPrecioUnitario(),
                                fp.getSubtotal()
                        ))
                        .toList();

        BigDecimal total = lineas.stream()
                .map(FacturaProductoResponseDto::subtotalProducto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FacturaResponseDto(
                facturaEliminada.getId(),
                facturaEliminada.getCliente().getNombre(),
                facturaEliminada.getCliente().getEmail(),
                facturaEliminada.getFecha(),
                total,
                lineas
        );

    }



    @Override
    public List<FacturaResumenResponseDto> encontrarFacturasEntreFechas(LocalDate inicio, LocalDate fin) {
        // 1. Convertir fechas naturales a rango completo del día
        LocalDateTime desde = inicio.atStartOfDay();
        LocalDateTime hasta = fin.atTime(LocalTime.MAX);
        List<Factura> facturasFechas = facturaRepository.findByFechaBetween(desde, hasta);

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
                .map(f -> {

                    BigDecimal base = totalesPorFactura.getOrDefault(
                            f.getId(), BigDecimal.ZERO
                    );

                    BigDecimal totalConIva = calcularTotalConIva(base, f.getIva());

                    return new FacturaResumenResponseDto(
                            f.getId(),
                            f.getCliente().getNombre(),
                            f.getCliente().getEmail(),
                            f.getFecha(),
                            totalConIva
                    );
                })
                .toList();
    }

    @Override
    public List<FacturaResumenResponseDto> encontrarFacturaPorFecha(LocalDate inicio, LocalDate fin) {
        // 1. Convertir fechas naturales a rango completo del día
        LocalDateTime desde = inicio.atStartOfDay();
        LocalDateTime hasta = fin.atTime(LocalTime.MAX);

        List<Factura> facturaPorFecha = facturaRepository.findByFechaBetween(desde, hasta);
        Map<UUID, BigDecimal> totalesPorFactura =
                facturaProductoRepository.obtenerTotalesPorFactura()
                        .stream()
                        .collect(Collectors.toMap(
                                fila -> (UUID) fila[0],
                                fila -> (BigDecimal) fila[1]
                        ));

        // Construir el DTO resumen
        return facturaPorFecha.stream()
                .map(f -> {

                    BigDecimal base = totalesPorFactura.getOrDefault(
                            f.getId(), BigDecimal.ZERO);

                    BigDecimal totalConIva = calcularTotalConIva(base, f.getIva());


                    return new FacturaResumenResponseDto(
                            f.getId(),
                            f.getCliente().getNombre(),
                            f.getCliente().getEmail(),
                            f.getFecha(),
                            totalConIva
                    );
                })
                .toList();
    }

    //Calcular IVA
    private BigDecimal calcularTotalConIva(BigDecimal base, BigDecimal iva) {
        BigDecimal importeIva = base.multiply(iva);
        return base.add(importeIva);
    }



    @Override
    public FacturaResponseDto obtenerFacturaCompleta(UUID id){
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Factura no encontrada"));

        // 2. Obtener las líneas (ENTIDADES) y mapearlas a DTO
        List<FacturaProductoResponseDto> lineas =
                facturaProductoRepository.findByFacturaId(factura.getId())
                        .stream()
                        .map(fp -> new FacturaProductoResponseDto(
                                fp.getId(),
                                fp.getProducto().getId(),
                                fp.getProducto().getNombre(),
                                fp.getCantidad(),
                                fp.getPrecioUnitario(),
                                fp.getSubtotal()
                        ))
                        .toList();
        //Total factura
        BigDecimal total = lineas.stream()
                .map(FacturaProductoResponseDto::subtotalProducto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Total con IVA
        BigDecimal totalConIva = calcularTotalConIva(total, factura.getIva());

        return new FacturaResponseDto(
                factura.getId(),
                factura.getCliente().getNombre(),
                factura.getCliente().getEmail(),
                factura.getFecha(),
                totalConIva,
                lineas
        );

    }


}
