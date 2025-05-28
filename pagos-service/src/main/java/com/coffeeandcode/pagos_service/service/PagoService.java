package com.coffeeandcode.pagos_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PagoService {
    private final String PEDIDOS_URL = "http://localhost:8082/pedidos/";

    @Autowired
    private WebClient webClient;

    public boolean marcarPedidoComoPagado(Long pedidoId) {
        try {
            webClient.put()
                    .uri(PEDIDOS_URL + pedidoId + "/pagar")
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
