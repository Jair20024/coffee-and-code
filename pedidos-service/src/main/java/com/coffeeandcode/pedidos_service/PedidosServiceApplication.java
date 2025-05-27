package com.coffeeandcode.pedidos_service;

import com.coffeeandcode.pedidos_service.entity.DetallePedido;
import com.coffeeandcode.pedidos_service.entity.Pedido;
import com.coffeeandcode.pedidos_service.repository.PedidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@SpringBootApplication
public class PedidosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidosServiceApplication.class, args);
    }

    /*
    @Bean
    CommandLineRunner initDB(PedidoRepository repo) {
        return args -> {
            Pedido pedido1 = Pedido.builder()
                    .clienteId(1L)
                    .estado("PENDIENTE")
                    .pagado(false)
                    .descripcion("Pedido de prueba")
                    .build();

            DetallePedido detalle1 = DetallePedido.builder()
                    .productoId(1L)
                    .cantidad(2)
                    .precioUnitario(10.0)
                    .pedido(pedido1)
                    .build();

            DetallePedido detalle2 = DetallePedido.builder()
                    .productoId(2L)
                    .cantidad(1)
                    .precioUnitario(12.5)
                    .pedido(pedido1)
                    .build();

            pedido1.setDetalles(Arrays.asList(detalle1, detalle2));

            repo.save(pedido1);
        };
    }*/

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
