package com.coffeeandcode.pedidos_service.dto;

import com.coffeeandcode.pedidos_service.entity.Pedido;

public class PedidoDTOFactory {
    public static PedidoDTO factoryMethod(Pedido pedido) {
        if (pedido == null) return null;
        switch (pedido.getEstado()) {
            case "PENDIENTE":
                return new PedidoPendienteDTO(pedido);
            case "EN_PREPARACION":
                return new PedidoEnPreparacionDTO(pedido);
            case "LISTO":
                return new PedidoListoDTO(pedido);
            case "ENTREGADO":
                return new PedidoEntregadoDTO(pedido);
            case "CANCELADO":
                return new PedidoCanceladoDTO(pedido);
            default:
                return new PedidoDTO(pedido);
        }
    }
}

