package com.coffeeandcode.productos_service.controller;

import com.coffeeandcode.productos_service.entity.Producto;
import com.coffeeandcode.productos_service.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerProducto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarStock(@PathVariable Long id, @RequestParam Integer stock) {
        return productoService.actualizarStock(id, stock)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

