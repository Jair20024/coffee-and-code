package com.coffeeandcode.clientes_service.repository;

import com.coffeeandcode.clientes_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCorreo(String correo);
}
