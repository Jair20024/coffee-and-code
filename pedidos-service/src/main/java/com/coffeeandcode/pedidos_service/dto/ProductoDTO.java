package com.coffeeandcode.pedidos_service.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
}

