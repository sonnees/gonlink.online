package online.gonlink.accountservice.service.base.impl;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.accountservice.config.AccountServiceConfig;
import online.gonlink.accountservice.dto.KafkaMessage;
import online.gonlink.accountservice.util.FormatLogMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import online.gonlink.accountservice.service.base.*;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Slf4j
public class ProducerServiceImpl implements ProducerService {
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private AccountServiceConfig config;

    @Override
    public void sendMessage(KafkaMessage message) {
        CompletableFuture<SendResult<String, KafkaMessage>> send = kafkaTemplate.send(config.getTopicName(), message);
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
