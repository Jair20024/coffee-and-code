package com.coffeeandcode.productos_service.service;

import com.coffeeandcode.productos_service.entity.Producto;
import com.coffeeandcode.productos_service.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProducto(Long id) {
        return productoRepository.findById(id);
    }

    public Optional<Producto> descontarStock(Long id, Integer cantidad) {
        return productoRepository.findById(id).map(producto -> {
            int nuevoStock = producto.getStock() - cantidad;
            if (nuevoStock < 0) {
                throw new RuntimeException("Stock insuficiente");
            }
            producto.setStock(nuevoStock);
            return productoRepository.save(producto);
        });
    }
}

