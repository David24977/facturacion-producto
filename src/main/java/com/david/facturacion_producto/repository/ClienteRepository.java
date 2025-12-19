package com.david.facturacion_producto.repository;

import com.david.facturacion_producto.dto.ClienteResponseDto;
import com.david.facturacion_producto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByNombreAndEmail(String nombre, String email);
    Optional<Cliente> findByDni(String dni);
    List<Cliente> findByNombreStartingWithIgnoreCaseOrDniStartingWithIgnoreCase(String nombre, String dni);

}
