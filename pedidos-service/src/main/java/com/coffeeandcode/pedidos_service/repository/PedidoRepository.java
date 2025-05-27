package com.coffeeandcode.pedidos_service.repository;

import com.coffeeandcode.pedidos_service.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
