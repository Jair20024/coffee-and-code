package com.coffeeandcode.pedidos_service.service;

import com.coffeeandcode.pedidos_service.entity.DetallePedido;
import com.coffeeandcode.pedidos_service.entity.Pedido;
import com.coffeeandcode.pedidos_service.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPedido(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido crearPedido(Pedido pedido) {
        // Asociar los detalles con el pedido antes de guardar
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(det -> det.setPedido(pedido));
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
}


