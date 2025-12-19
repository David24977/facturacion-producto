package com.david.facturacion_producto.service.serviceImpl;

import com.david.facturacion_producto.dto.ClienteRequestDto;
import com.david.facturacion_producto.dto.ClienteResponseDto;
import com.david.facturacion_producto.dto.ClienteResponsePublicDto;
import com.david.facturacion_producto.model.Cliente;
import com.david.facturacion_producto.repository.ClienteRepository;
import com.david.facturacion_producto.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<ClienteResponsePublicDto> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToPublicDto)
                .toList();
    }

    @Override
    public ClienteResponseDto crearCliente(ClienteRequestDto clienteRequestDto) {
        Cliente cliente = new Cliente();

        clienteRepository.findByDni(clienteRequestDto.dni())
                        .ifPresent(cd -> {
                            throw new ResponseStatusException(
                                HttpStatus.CONFLICT, "Cliente con ese dni ya existe");
                        });
        cliente.setNombre(clienteRequestDto.nombre());
        cliente.setDni(clienteRequestDto.dni());
        cliente.setEmail(clienteRequestDto.email());
       

        clienteRepository.save(cliente);
        return mapToResponseDto(cliente);
    }

    @Override
    public ClienteResponseDto modificarParcialCliente(UUID id, ClienteRequestDto clienteRequestDto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        if(clienteRequestDto.nombre() != null){
            cliente.setNombre(clienteRequestDto.nombre());
        }

        if(clienteRequestDto.email() != null){
            cliente.setEmail(clienteRequestDto.email());
        }
        clienteRepository.save(cliente);

        return mapToResponseDto(cliente);
    }

    @Override
    public ClienteResponsePublicDto eliminarCliente(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        clienteRepository.delete(cliente);

        return mapToPublicDto(cliente);
    }

    @Override
    public ClienteResponseDto buscarPorNombreYEmail(String nombre, String email) {
        Cliente cliente = clienteRepository.findByNombreAndEmail(nombre, email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        return mapToResponseDto(cliente);
    }

    @Override
    public ClienteResponseDto buscarPorDni(String dni) {
        Cliente cliente = clienteRepository.findByDni(dni)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        return mapToResponseDto(cliente);

    }

    @Override
    public List<ClienteResponseDto>  buscarPorNombreODniAutocomplete(String nombre, String dni) {

        if (nombre == null && dni == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe indicar al menos nombre o dni"
            );
        }

        return clienteRepository
                .findByNombreStartingWithIgnoreCaseOrDniStartingWithIgnoreCase(nombre, dni)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }



    private ClienteResponseDto mapToResponseDto(Cliente cliente){
        return new ClienteResponseDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getDni(),
                cliente.getEmail()
        );
    }

    private ClienteResponsePublicDto mapToPublicDto(Cliente cliente){
        return new ClienteResponsePublicDto(
                cliente.getNombre(),
                cliente.getEmail()
        );
    }
}
