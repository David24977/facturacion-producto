package com.david.facturacion_producto.service.serviceImpl;

import com.david.facturacion_producto.dto.ProductoRequestDto;
import com.david.facturacion_producto.dto.ProductoResponseDto;
import com.david.facturacion_producto.model.Producto;
import com.david.facturacion_producto.repository.ProductoRepository;
import com.david.facturacion_producto.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }


    @Override
    public List<ProductoResponseDto> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductoResponseDto crearProducto(ProductoRequestDto productoRequestDto) {
       Producto creado = new Producto();
       creado.setNombre(productoRequestDto.getNombreProducto());
       creado.setPrecio(productoRequestDto.getPrecioProducto());

       productoRepository.save(creado);
       return mapToResponse(creado);
    }

    @Override
    public ProductoResponseDto modificarProducto(UUID id, ProductoRequestDto productoRequestDto) {
        Producto modificado = productoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        modificado.setNombre(productoRequestDto.getNombreProducto());
        modificado.setPrecio(productoRequestDto.getPrecioProducto());
        productoRepository.save(modificado);
        return mapToResponse(modificado);
    }

    @Override
    public ProductoResponseDto modificarParcialProducto(UUID id, ProductoRequestDto productoRequestDto) {
        Producto modificadoParcial = productoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        if(productoRequestDto.getNombreProducto() != null){
            modificadoParcial.setNombre(productoRequestDto.getNombreProducto());
        }
        if(productoRequestDto.getPrecioProducto() != null){
            modificadoParcial.setPrecio(productoRequestDto.getPrecioProducto());
        }

        productoRepository.save(modificadoParcial);
        return mapToResponse(modificadoParcial);
    }

    @Override
    public ProductoResponseDto eliminarProducto(UUID id) {
        Producto eliminado = productoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        productoRepository.delete(eliminado);
        return mapToResponse(eliminado);
    }

    @Override
    public ProductoResponseDto buscarProductoPorNombre(String nombre) {
        Producto buscarNombre = productoRepository.findByNombre(nombre)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        return mapToResponse(buscarNombre);
    }

    private ProductoResponseDto mapToResponse(Producto producto){
        return new ProductoResponseDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio()
        );
    }
}
