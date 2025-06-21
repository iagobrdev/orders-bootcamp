package com.br.bootcamp.orders.controller.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Orders API")
                        .description("API REST para gerenciamento de pedidos, clientes e produtos. " +
                                "Desenvolvida seguindo o padrão arquitetural MVC (Model-View-Controller).")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Bootcamp Architecture Software")
                                .email("contato@bootcamp.com")
                                .url("https://github.com/bootcamp/orders"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8085")
                                .description("Servidor de Desenvolvimento"),
                        new Server()
                                .url("https://api.orders.com")
                                .description("Servidor de Produção")
                ));
    }
} 