package com.coffeeandcode.pagos_service.controller;

import com.coffeeandcode.pagos_service.entity.Pago;
import com.coffeeandcode.pagos_service.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @PostMapping
    public ResponseEntity<Pago> registrarPago(@RequestParam Long pedidoId, @RequestParam Double monto) {
        Pago pago = pagoService.registrarPago(pedidoId, monto);
        return ResponseEntity.ok(pago);
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Pago> obtenerPagoPorPedido(@PathVariable Long pedidoId) {
        Optional<Pago> pago = pagoService.obtenerPagoPorPedido(pedidoId);
        return pago.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
