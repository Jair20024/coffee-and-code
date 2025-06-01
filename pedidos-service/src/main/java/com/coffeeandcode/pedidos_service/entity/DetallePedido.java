package com.coffeeandcode.pedidos_service.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;
    private Integer cantidad;
    private String nombreProducto;
    private Double precioUnitario; 

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonBackReference  // <--- Evita la recursión hacia Pedido al serializar JSON
    private Pedido pedido;
    
   
}
