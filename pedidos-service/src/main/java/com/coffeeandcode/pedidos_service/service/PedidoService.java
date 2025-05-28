package com.coffeeandcode.pedidos_service.service;

import com.coffeeandcode.pedidos_service.dto.ClienteDTO;
import com.coffeeandcode.pedidos_service.dto.ProductoDTO;
import com.coffeeandcode.pedidos_service.entity.DetallePedido;
import com.coffeeandcode.pedidos_service.entity.Pedido;
import com.coffeeandcode.pedidos_service.repository.PedidoRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private WebClient webClient;

    private final String CLIENTES_URL = "http://localhost:8083/clientes/";
    private final String PRODUCTOS_URL = "http://localhost:8081/productos/";

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPedido(Long id) {
        return pedidoRepository.findById(id);
    }

    @CircuitBreaker(name = "clientesServiceCB", fallbackMethod = "fallbackCliente")
    @Retry(name = "clientesServiceRetry")
    private ClienteDTO consultarCliente(Long clienteId) {
        return webClient.get()
                .uri(CLIENTES_URL + clienteId)
                .retrieve()
                .bodyToMono(ClienteDTO.class)
                .block();
    }

    public ClienteDTO fallbackCliente(Long clienteId, Throwable t) {
        throw new RuntimeException("Servicio de clientes no disponible. No se pudo validar el cliente.");
    }

    @CircuitBreaker(name = "productosServiceCB", fallbackMethod = "fallbackProducto")
    @Retry(name = "productosServiceRetry")
    private ProductoDTO consultarProducto(Long productoId) {
        return webClient.get()
                .uri(PRODUCTOS_URL + productoId)
                .retrieve()
                .bodyToMono(ProductoDTO.class)
                .block();
    }

    public ProductoDTO fallbackProducto(Long productoId, Throwable t) {
        throw new RuntimeException("Servicio de productos no disponible. No se pudo validar el producto.");
    }

    @CircuitBreaker(name = "productosServiceCB", fallbackMethod = "fallbackActualizarStock")
    @Retry(name = "productosServiceRetry")
    private void descontarStock(Long productoId, Integer cantidad) {
        webClient.put()
                .uri(PRODUCTOS_URL + productoId + "?cantidad=" + cantidad)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(),
                    response -> response.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Error al actualizar el stock: " + body)))
                .bodyToMono(Void.class)
                .block();
    }

    public void fallbackActualizarStock(Long productoId, Integer cantidad, Throwable t) {
        throw new RuntimeException("Servicio de productos no disponible. No se pudo actualizar el stock.");
    }

    public Pedido crearPedido(Pedido pedido) {
        // Validar cliente
        ClienteDTO cliente = consultarCliente(pedido.getClienteId());
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }
        // Validar productos y stock
        if (pedido.getDetalles() != null) {
            for (DetallePedido det : pedido.getDetalles()) {
                ProductoDTO producto = consultarProducto(det.getProductoId());
                if (producto == null) {
                    throw new RuntimeException("Producto no encontrado: " + det.getProductoId());
                }
                if (producto.getStock() == null || producto.getStock() < det.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
                }
                descontarStock(det.getProductoId(), det.getCantidad());
                det.setNombreProducto(producto.getNombre());
                det.setPedido(pedido);
            }
        }
        pedido.setEstado("PENDIENTE");
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> cancelarPedido(Long id) {
        return pedidoRepository.findById(id).map(pedido -> {
            if ("PENDIENTE".equals(pedido.getEstado())) {
                pedido.setEstado("CANCELADO");
                return pedidoRepository.save(pedido);
            }
            return pedido;
        });
    }

    public Optional<Pedido> marcarComoPagado(Long id) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setPagado(true);
            if ("PENDIENTE".equals(pedido.getEstado())) {
                pedido.setEstado("LISTO");
            }
            return pedidoRepository.save(pedido);
        });
    }
}
