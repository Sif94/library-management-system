package org.baouz.libraryapi.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {


    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consume(EmailDto email) {
        log.info("Email consumed: {}", email.subject());
    }

}
