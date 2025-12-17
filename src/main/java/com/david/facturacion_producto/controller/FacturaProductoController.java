package com.david.facturacion_producto.controller;

import com.david.facturacion_producto.dto.FacturaProductoRequestDto;
import com.david.facturacion_producto.dto.FacturaProductoResponseDto;
import com.david.facturacion_producto.service.FacturaProductoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lineas")
public class FacturaProductoController {
    private final FacturaProductoService facturaProductoService;


    public FacturaProductoController(FacturaProductoService facturaProductoService) {
        this.facturaProductoService = facturaProductoService;

    }

    @GetMapping("/factura/{facturaId}")
    public List<FacturaProductoResponseDto> findByFacturaId(@PathVariable UUID facturaId){
        return facturaProductoService.findByFacturaId(facturaId);
    }

    @PostMapping
    public FacturaProductoResponseDto crearLinea(@Valid @RequestBody FacturaProductoRequestDto facturaProductoRequestDto){
        return facturaProductoService.crearLinea(facturaProductoRequestDto);
    }

    @PatchMapping("/{id}")
    public FacturaProductoResponseDto modificarParcialLinea(@PathVariable UUID id, @RequestBody FacturaProductoRequestDto facturaProductoRequestDto){
        return facturaProductoService.modificarParcialLinea(id, facturaProductoRequestDto);
    }

    @DeleteMapping("{id}")
    public FacturaProductoResponseDto eliminarLinea(@PathVariable UUID id){
        return facturaProductoService.eliminarLinea(id);
    }

}
