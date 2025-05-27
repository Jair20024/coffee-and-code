package com.coffeeandcode.clientes_service.service;

import com.coffeeandcode.clientes_service.entity.Cliente;
import com.coffeeandcode.clientes_service.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente registrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> obtenerCliente(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> actualizarPerfil(Long id, Cliente nuevoPerfil) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(nuevoPerfil.getNombre());
            cliente.setCorreo(nuevoPerfil.getCorreo());
            return clienteRepository.save(cliente);
        });
    }

    public Optional<Cliente> autenticar(String correo, String contraseña) {
        return clienteRepository.findByCorreo(correo)
                .filter(cliente -> cliente.getContraseña().equals(contraseña));
    }
}
