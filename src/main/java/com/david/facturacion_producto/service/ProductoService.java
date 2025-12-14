package com.david.facturacion_producto.service;

import com.david.facturacion_producto.dto.ProductoRequestDto;
import com.david.facturacion_producto.dto.ProductoResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductoService {
    List<ProductoResponseDto> listarProductos();

    ProductoResponseDto crearProducto(ProductoRequestDto productoRequestDto);

    ProductoResponseDto modificarProducto(UUID id, ProductoRequestDto productoRequestDto);

    ProductoResponseDto modificarParcialProducto(UUID id, ProductoRequestDto productoRequestDto);

    ProductoResponseDto eliminarProducto(UUID id);

    ProductoResponseDto buscarProductoPorNombre(String nombre);
}
