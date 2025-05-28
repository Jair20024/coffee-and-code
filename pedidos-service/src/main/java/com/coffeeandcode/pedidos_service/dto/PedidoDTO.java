package com.coffeeandcode.pedidos_service.dto;

import com.coffeeandcode.pedidos_service.entity.Pedido;
import com.coffeeandcode.pedidos_service.entity.DetallePedido;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private String estado;
    private Boolean pagado;
    private String descripcion;
    private List<DetallePedido> detalles;
    // Puedes agregar más campos si lo necesitas

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.clienteId = pedido.getClienteId();
        this.estado = pedido.getEstado();
        this.pagado = pedido.getPagado();
        this.descripcion = pedido.getDescripcion();
        this.detalles = pedido.getDetalles();
    }
}


