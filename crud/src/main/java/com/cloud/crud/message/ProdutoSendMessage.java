package com.cloud.crud.message;

import com.cloud.crud.dataVO.ProdutoVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProdutoSendMessage {

    @Value("${crud.rabbitmq.exchange}")
    private String exchange;

    @Value("${crud.rabbitmq.routingKey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProdutoSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ProdutoVO produtoVO) {
        rabbitTemplate.convertAndSend(exchange, routingKey, produtoVO);
    }
}
