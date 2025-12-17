package com.david.facturacion_producto.controller;

import com.david.facturacion_producto.dto.ProductoRequestDto;
import com.david.facturacion_producto.dto.ProductoResponseDto;
import com.david.facturacion_producto.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoResponseDto> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("{id}")
    public ProductoResponseDto buscarPorId(@PathVariable UUID id){
        return productoService.buscarPorId(id);
    }

    @PostMapping
    public ProductoResponseDto crearProducto(@Valid @RequestBody ProductoRequestDto productoRequestDto) {
        return productoService.crearProducto(productoRequestDto);
    }

    @PutMapping("/{id}")
    public ProductoResponseDto modificarProducto(@PathVariable UUID id,
                                                 @Valid @RequestBody ProductoRequestDto productoRequestDto) {
        return productoService.modificarProducto(id, productoRequestDto);
    }

    @PatchMapping("/{id}")
    public ProductoResponseDto modificarParcialProducto(@PathVariable UUID id,
                                                        @RequestBody ProductoRequestDto productoRequestDto){
        return productoService.modificarParcialProducto(id, productoRequestDto);
    }

    @DeleteMapping("/{id}")
    public ProductoResponseDto eliminarProducto(@PathVariable UUID id){
        return productoService.eliminarProducto(id);
    }

    @GetMapping(value = "/buscar", params = "nombre")//Otra forma de hacerlo con @Requestparam
    public ProductoResponseDto buscarProductoPorNombre(@RequestParam String nombre){
        return productoService.buscarProductoPorNombre(nombre);
    }
}
