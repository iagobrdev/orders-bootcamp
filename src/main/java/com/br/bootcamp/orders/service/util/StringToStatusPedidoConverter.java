package com.br.bootcamp.orders.service.util;

import com.br.bootcamp.orders.model.enums.StatusPedido;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToStatusPedidoConverter implements Converter<String, StatusPedido> {

    @Override
    public StatusPedido convert(String source) {
        if (source.isEmpty()) {
            return null;
        }

        for (StatusPedido status : StatusPedido.values()) {
            // Tenta fazer a conversão pela descrição (ex: "Pendente")
            if (status.getDescricao().equalsIgnoreCase(source)) {
                return status;
            }
            // Tenta fazer a conversão pelo nome do enum (ex: "PENDENTE")
            if (status.name().equalsIgnoreCase(source)) {
                return status;
            }
        }
        
        throw new IllegalArgumentException("Status de pedido inválido: " + source);
    }
} 