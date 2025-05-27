package com.coffeeandcode.clientes_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coffeeandcode.clientes_service.entity.Cliente;
import com.coffeeandcode.clientes_service.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientesServiceApplication.class, args);
    }

    // Datos de prueba en H2
    @Bean
    CommandLineRunner initDB(ClienteRepository repo) {
        return args -> {
            repo.save(Cliente.builder()
                    .nombre("Juan Pérez")
                    .correo("juan@correo.com")
                    .contraseña("1234")
                    .build());

            repo.save(Cliente.builder()
                    .nombre("Ana Torres")
                    .correo("ana@correo.com")
                    .contraseña("abcd")
                    .build());
        };
    }
}
