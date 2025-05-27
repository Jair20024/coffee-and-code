package com.coffeeandcode.pagos_service.repository;

import com.coffeeandcode.pagos_service.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByPedidoId(Long pedidoId);
}
