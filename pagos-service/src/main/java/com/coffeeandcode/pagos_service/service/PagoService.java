package com.coffeeandcode.pagos_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PagoService {
    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);
    private final String PEDIDOS_URL = "http://localhost:8082/pedidos/";

    @Autowired
    private WebClient webClient;

    @Retry(name = "pagosServiceRetry")
    @CircuitBreaker(name = "pagosServiceCB", fallbackMethod = "fallbackMarcarPedidoComoPagado")
    public boolean marcarPedidoComoPagado(Long pedidoId) {
        webClient.put()
                .uri(PEDIDOS_URL + pedidoId + "/pagar")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        return true;
    }

    public boolean fallbackMarcarPedidoComoPagado(Long pedidoId, Throwable t) {
        logger.error("Error al marcar pedido como pagado (fallback): {}", t.getMessage());
        return false;
    }
}
