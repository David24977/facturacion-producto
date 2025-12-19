package com.david.facturacion_producto.service;

import com.david.facturacion_producto.dto.ClienteRequestDto;
import com.david.facturacion_producto.dto.ClienteResponseDto;
import com.david.facturacion_producto.dto.ClienteResponsePublicDto;

import java.util.List;
import java.util.UUID;

public interface ClienteService {

    List<ClienteResponsePublicDto> listarClientes();

    ClienteResponseDto crearCliente(ClienteRequestDto clienteRequestDto);

    ClienteResponseDto modificarParcialCliente(UUID id, ClienteRequestDto clienteRequestDto);

    ClienteResponsePublicDto eliminarCliente(UUID id);

    ClienteResponseDto buscarPorNombreYEmail(String nombre, String email);

    ClienteResponseDto buscarPorDni(String dni);

    List<ClienteResponseDto> buscarPorNombreODniAutocomplete(String nombre, String dni);


}
