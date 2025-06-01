package com.coffeeandcode.pedidos_service.service;

import com.coffeeandcode.pedidos_service.dto.ClienteDTO;
import com.coffeeandcode.pedidos_service.dto.ProductoDTO;
import com.coffeeandcode.pedidos_service.entity.DetallePedido;
import com.coffeeandcode.pedidos_service.entity.Pedido;
import com.coffeeandcode.pedidos_service.repository.PedidoRepository;
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

    public Pedido crearPedido(Pedido pedido) {
        // Validar cliente
        ClienteDTO cliente;
        try {
            cliente = webClient.get()
                    .uri(CLIENTES_URL + pedido.getClienteId())
                    .retrieve()
                    .bodyToMono(ClienteDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Cliente no encontrado");
        }
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }
        // Validar productos y stock
        if (pedido.getDetalles() != null) {
            for (DetallePedido det : pedido.getDetalles()) {
                ProductoDTO producto;
                try {
                    producto = webClient.get()
                            .uri(PRODUCTOS_URL + det.getProductoId())
                            .retrieve()
                            .bodyToMono(ProductoDTO.class)
                            .block();
                } catch (Exception e) {
                    throw new RuntimeException("Producto no encontrado: " + det.getProductoId());
                }
                if (producto == null) {
                    throw new RuntimeException("Producto no encontrado: " + det.getProductoId());
                }
                // Verificar stock suficiente
                if (producto.getStock() == null || producto.getStock() < det.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
                }
                // Descontar el stock llamando al microservicio de productos
                try {
                    webClient.put()
                            .uri(PRODUCTOS_URL + det.getProductoId() + "?cantidad=" + det.getCantidad())
                            .retrieve()
                            .onStatus(status -> !status.is2xxSuccessful(),
                                response -> response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("Error al actualizar el stock: " + body)))
                            .bodyToMono(Void.class)
                            .block();
                } catch (Exception e) {
                    throw new RuntimeException("Error al actualizar el stock del producto: " + producto.getNombre() + " - " + e.getMessage());
                }
                // Asignar el precioUnitario desde el microservicio de productos
                det.setNombreProducto(producto.getNombre());
                det.setPrecioUnitario(producto.getPrecio());
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
            pedido.setEstado("PAGADO"); 
            return pedidoRepository.save(pedido);
        });
    }

}

