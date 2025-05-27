package com.coffeeandcode.clientes_service.controller;

import com.coffeeandcode.clientes_service.entity.Cliente;
import com.coffeeandcode.clientes_service.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/registrar")
    public ResponseEntity<Cliente> registrar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.registrarCliente(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        return clienteService.obtenerCliente(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarPerfil(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.actualizarPerfil(id, cliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@RequestBody Map<String, String> credenciales) {
        String correo = credenciales.get("correo");
        String contraseña = credenciales.get("contraseña");

        return clienteService.autenticar(correo, contraseña)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}


