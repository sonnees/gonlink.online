package gonlink.online.shortenservice.service.base;

import gonlink.online.shortenservice.dto.KafkaMessage;
import gonlink.online.shortenservice.util.FormatLogMessage;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ProducerService {
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Value("${shorten-service.kafka.topic}")
    private String topicName;

    public ProducerService(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(KafkaMessage message) {
        CompletableFuture<SendResult<String, KafkaMessage>> send = kafkaTemplate.send(topicName, message);
        send.whenComplete((result, e)->{
            if (e != null){
                // lưu vết để có thể update lại dữ liệu.
                log.error(FormatLogMessage.formatLogMessage(
                        this.getClass().getSimpleName(),
                        "sendMessage",
                        "Unexpected error: " + message,
                        e
                ));
                throw new StatusRuntimeException(Status.INTERNAL.withDescription("Kafka Error"));
            }
        });
    }
}
