package gonlink.online.shortenservice.service.base;

import gonlink.online.shortenservice.dto.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ConsumerService {

    @KafkaListener(topics = "${shorten-service.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(KafkaMessage message) {
        System.out.println("Received message: " + message);
    }

}
