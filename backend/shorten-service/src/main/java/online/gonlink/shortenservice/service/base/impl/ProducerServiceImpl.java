package online.gonlink.shortenservice.service.base.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.gonlink.shortenservice.config.AccountServiceConfig;
import online.gonlink.shortenservice.dto.KafkaMessage;
import online.gonlink.shortenservice.service.base.ProducerService;
import online.gonlink.shortenservice.util.FormatLogMessage;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ProducerServiceImpl implements ProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final AccountServiceConfig config;

    public ProducerServiceImpl(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, AccountServiceConfig config) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.config = config;
    }

    @Override
    public void sendMessage(KafkaMessage message) {
        CompletableFuture<SendResult<String, String>> send = null;
        try {
            String mes = objectMapper.writeValueAsString(message);
            send = kafkaTemplate.send(config.getTOPIC_NAME(), mes);
        } catch (JsonProcessingException e) {
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "sendMessage < JsonProcessingException",
                    "Unexpected error: " + message,
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Kafka Error"));
        }

        send.whenComplete((result, e)->{
            if (e != null){
                // lưu vết để có thể update lại dữ liệu.
                log.error(FormatLogMessage.formatLogMessage(
                        this.getClass().getSimpleName(),
                        "sendMessage < whenComplete",
                        "Unexpected error: " + message,
                        e
                ));
                throw new StatusRuntimeException(Status.INTERNAL.withDescription("Kafka Error"));
            }
        });
    }
}
