package com.coffeeandcode.pagos_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PagoService {

    @Autowired
    private WebClient webClient;

    private final String PEDIDOS_URL = "http://localhost:8082/pedidos/";

    public boolean marcarPedidoComoPagado(Long pedidoId) {
        try {
            webClient.put()
                    .uri(PEDIDOS_URL + pedidoId + "/marcarPagado")
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block(); // Llamada síncrona

            return true;
        } catch (Exception e) {
            System.err.println("Error al marcar el pedido como pagado: " + e.getMessage());
            return false;
        }
    }
}


