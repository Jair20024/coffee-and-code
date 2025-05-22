package com.coffeeandcode.productos_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Para datos de prueba en H2
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.coffeeandcode.productos_service.entity.Producto;
import com.coffeeandcode.productos_service.repository.ProductoRepository;

@SpringBootApplication
public class ProductosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductosServiceApplication.class, args);
	}

	//Datos de prueba en H2
	@Bean
	CommandLineRunner initDB(ProductoRepository repo) {
		return args -> {
			repo.save(Producto.builder()
					.nombre("Café Americano")
					.descripcion("Café negro")
					.precio(10.0)
					.stock(100)
					.build());
			repo.save(Producto.builder()
					.nombre("Latte")
					.descripcion("Café con leche")
					.precio(12.5)
					.stock(50)
					.build());
		};
	}
}

