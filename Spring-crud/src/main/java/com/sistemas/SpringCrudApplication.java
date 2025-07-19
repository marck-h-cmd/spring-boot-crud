package com.sistemas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
@EntityScan("entidad")
@EnableJpaRepositories(basePackages = "repositorio") 
@ComponentScan(basePackages = {
    "com.sistemas",
    "controlador",
    "servicio",
    "repositorio"
})
@SpringBootApplication
public class SpringCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCrudApplication.class, args);
	}

}
