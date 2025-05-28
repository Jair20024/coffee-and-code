package com.coffeeandcode.pedidos_service.dto;

import com.coffeeandcode.pedidos_service.entity.Pedido;

public class PedidoPendienteDTO extends PedidoDTO {
    public PedidoPendienteDTO(Pedido pedido) {
        super(pedido);
    }
    // Puedes agregar campos o métodos específicos para pedidos pendientes
}

