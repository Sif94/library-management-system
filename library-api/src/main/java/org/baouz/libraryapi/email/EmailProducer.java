package org.baouz.libraryapi.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailProducer {
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.queue.name}")
    private String queueName;
    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public void sendEmail(EmailDto emailDto) {
        log.info("Sending email to -> %s: {}", emailDto.to());
        rabbitTemplate.convertAndSend(exchangeName, routingKey, emailDto);
    }



}
