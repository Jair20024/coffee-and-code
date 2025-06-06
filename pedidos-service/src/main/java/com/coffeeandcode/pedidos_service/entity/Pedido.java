package com.coffeeandcode.pedidos_service.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    private String estado; // PENDIENTE, EN_PREPARACION, LISTO, ENTREGADO, CANCELADO ,PAGADO

    private Boolean pagado = false;

    private String descripcion;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference  // para evitar problemas de recursión JSON
    private List<DetallePedido> detalles;

}
