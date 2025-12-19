package com.david.facturacion_producto.controller;

import com.david.facturacion_producto.dto.ClienteRequestDto;
import com.david.facturacion_producto.dto.ClienteResponseDto;
import com.david.facturacion_producto.dto.ClienteResponsePublicDto;
import com.david.facturacion_producto.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteResponsePublicDto> listarClientes(){
        return clienteService.listarClientes();
    }

    @PostMapping
    public ClienteResponseDto crearCliente(@Valid @RequestBody ClienteRequestDto clienteRequestDto){
        return clienteService.crearCliente(clienteRequestDto);
    }

    @PatchMapping("/{id}")
    public ClienteResponseDto modificarParcialCliente(@PathVariable UUID id, @RequestBody ClienteRequestDto clienteRequestDto){
        return clienteService.modificarParcialCliente(id, clienteRequestDto);
    }

    @DeleteMapping("/{id}")
    public ClienteResponsePublicDto eliminarCliente(@PathVariable UUID id){
        return clienteService.eliminarCliente(id);
    }

    @GetMapping(value = "/buscar", params = "{nombre, email}")
    public ClienteResponseDto buscarPorNombreYEmail(@RequestParam String nombre, @RequestParam String email){
        return clienteService.buscarPorNombreYEmail(nombre, email);
    }

    @GetMapping(value = "/dni", params = "dni")
    public ClienteResponseDto buscarPorDni(@RequestParam String dni){
        return clienteService.buscarPorDni(dni);
    }

    @GetMapping(value = "/autocomplete")
    public List<ClienteResponseDto> buscarPorNombreODniAutocomplete(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String dni){
        if (nombre == null && dni == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe indicar al menos nombre o dni"
            );
        }
        return clienteService.buscarPorNombreODniAutocomplete(nombre, dni);

    }
}
