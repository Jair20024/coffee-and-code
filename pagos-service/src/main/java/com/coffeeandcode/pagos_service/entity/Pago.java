package com.coffeeandcode.pagos_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;
    private Double monto;

    @Enumerated(EnumType.STRING)
    private EstadoPago estado;
    
    public enum EstadoPago {
        PENDIENTE, PAGADO, FALLIDO
    }
}
