package com.coffeeandcode.pagos_service.controller;

import com.coffeeandcode.pagos_service.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @PutMapping("/marcar-pagado/{pedidoId}")
    public ResponseEntity<Object> marcarPedidoComoPagado(@PathVariable Long pedidoId) {
        boolean exito = pagoService.marcarPedidoComoPagado(pedidoId);
        if (exito) {
            return ResponseEntity.ok(java.util.Map.of("mensaje", "Pedido marcado como pagado"));
        } else {
            return ResponseEntity.status(500).body(java.util.Map.of("mensaje", "Error al marcar el pedido como pagado"));
        }
    }
}
