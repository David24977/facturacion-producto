package com.david.facturacion_producto.controller;

import com.david.facturacion_producto.dto.FacturaRequestDto;
import com.david.facturacion_producto.dto.FacturaResponseDto;
import com.david.facturacion_producto.dto.FacturaResumenResponseDto;
import com.david.facturacion_producto.service.FacturaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/facturas")
public class FacturaController {
    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public List<FacturaResumenResponseDto> listarFacturas(){
        return facturaService.listarFacturas();
    }

    @GetMapping("/{id}")
    public FacturaResponseDto obtenerFacturaCompleta(@PathVariable UUID id){
        return facturaService.obtenerFacturaCompleta(id);
    }

    @PostMapping
    public FacturaResumenResponseDto crearFactura(@Valid @RequestBody FacturaRequestDto facturaRequestDto){
        return facturaService.crearFactura(facturaRequestDto);
    }

    @PatchMapping("/{id}")
    public FacturaResumenResponseDto modificarParcialFactura(@PathVariable UUID id,
                                                             @RequestBody FacturaRequestDto facturaRequestDto){
        return facturaService.modificarParcialFactura(id, facturaRequestDto);
    }

    @DeleteMapping("/{id}")
    public FacturaResponseDto eliminarFactura(@PathVariable UUID id){
        return facturaService.eliminarFactura(id);
    }

    @GetMapping(value = "/buscar", params = {"inicio", "fin"})
    public List<FacturaResumenResponseDto> encontrarFacturasEntreFechas(@RequestParam LocalDateTime inicio,
                                                                        @RequestParam LocalDateTime fin){
        return facturaService.encontrarFacturasEntreFechas(inicio, fin);
    }

    @GetMapping(value = "/buscar", params = "fecha")
    public List<FacturaResumenResponseDto> encontrarFacturaPorFecha(@RequestParam LocalDateTime fecha){
        return facturaService.encontrarFacturaPorFecha(fecha);
    }



}
