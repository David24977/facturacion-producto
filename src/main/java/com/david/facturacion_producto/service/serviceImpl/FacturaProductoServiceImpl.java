package com.david.facturacion_producto.service.serviceImpl;

import com.david.facturacion_producto.dto.FacturaProductoRequestDto;
import com.david.facturacion_producto.dto.FacturaProductoResponseDto;
import com.david.facturacion_producto.model.Factura;
import com.david.facturacion_producto.model.FacturaProducto;
import com.david.facturacion_producto.model.Producto;
import com.david.facturacion_producto.repository.FacturaProductoRepository;
import com.david.facturacion_producto.repository.FacturaRepository;
import com.david.facturacion_producto.repository.ProductoRepository;
import com.david.facturacion_producto.service.FacturaProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.UUID;

@Service
public class FacturaProductoServiceImpl implements FacturaProductoService {
    private final FacturaProductoRepository facturaProductoRepository;
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;

    public FacturaProductoServiceImpl(FacturaProductoRepository facturaProductoRepository, FacturaRepository facturaRepository, ProductoRepository productoRepository) {
        this.facturaProductoRepository = facturaProductoRepository;
        this.facturaRepository = facturaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<FacturaProductoResponseDto> findByFacturaId(UUID facturaId) {

        return facturaProductoRepository.findByFacturaId(facturaId)
                .stream()
                .map(this::mapToResponseFacturaProducto)
                .toList();
    }

    @Override
    public FacturaProductoResponseDto crearLinea(FacturaProductoRequestDto facturaProductoRequestDto) {

       Factura factura = facturaRepository.findById(facturaProductoRequestDto.getFacturaId())
               .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada"));
       Producto producto = productoRepository.findById(facturaProductoRequestDto.getProductoId())
               .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrada"));
       FacturaProducto facturaProducto = new FacturaProducto();
       facturaProducto.setFactura(factura);
       facturaProducto.setProducto(producto);
       facturaProducto.setCantidad(facturaProductoRequestDto.getCantidadProducto());
       facturaProducto.setPrecioUnitario(producto.getPrecio());

       facturaProductoRepository.save(facturaProducto);

       return mapToResponseFacturaProducto(facturaProducto);


    }

    @Override
    public FacturaProductoResponseDto modificarParcialLinea(UUID id, FacturaProductoRequestDto facturaProductoRequestDto) {
        FacturaProducto linea = facturaProductoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada"));
        if(facturaProductoRequestDto.getCantidadProducto() != null){
            linea.setCantidad(facturaProductoRequestDto.getCantidadProducto());
        }
        facturaProductoRepository.save(linea);
        return mapToResponseFacturaProducto(linea);
    }

    @Override
    public FacturaProductoResponseDto eliminarLinea(UUID id) {
        FacturaProducto eliminarLinea = facturaProductoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada"));

        facturaProductoRepository.delete(eliminarLinea);
        return mapToResponseFacturaProducto(eliminarLinea);
    }

    public FacturaProductoResponseDto mapToResponseFacturaProducto(FacturaProducto facturaProducto){
                return new FacturaProductoResponseDto(
                        facturaProducto.getId(),
                        facturaProducto.getProducto().getId(),
                        facturaProducto.getProducto().getNombre(),
                        facturaProducto.getCantidad(),
                        facturaProducto.getPrecioUnitario(),
                        facturaProducto.getSubtotal()
                );
    }
}
