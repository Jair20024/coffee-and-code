package com.coffeeandcode.productos_service.repository;

import com.coffeeandcode.productos_service.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

