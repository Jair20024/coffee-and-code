package com.coffeeandcode.pagos_service.service;

import com.coffeeandcode.pagos_service.entity.Pago;
import com.coffeeandcode.pagos_service.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public Pago registrarPago(Long pedidoId, Double monto) {
        Pago pago = Pago.builder()
                .pedidoId(pedidoId)
                .monto(monto)
                .estado(Pago.EstadoPago.PAGADO) // Simulación de éxito
                .build();

        return pagoRepository.save(pago);
    }

    public Optional<Pago> obtenerPagoPorPedido(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId);
    }
}
