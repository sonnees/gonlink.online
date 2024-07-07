package online.gonlink.accountservice.service.base.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.gonlink.accountservice.dto.KafkaAppendUrl;
import online.gonlink.accountservice.dto.KafkaIncreaseTraffic;
import online.gonlink.accountservice.dto.KafkaMessage;
import online.gonlink.accountservice.service.TrafficService;
import online.gonlink.accountservice.service.base.ConsumerService;
import online.gonlink.accountservice.service.impl.AccountServiceImpl;
import online.gonlink.accountservice.util.FormatLogMessage;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

    private final AccountServiceImpl accountService;
    private final TrafficService trafficService;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "${account-service.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        KafkaMessage kafkaMessage = null;
        try {
            kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);
        } catch (JsonProcessingException e) {
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "listen < JsonProcessingException",
                    "Unexpected error: " + message,
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Kafka Error"));
        }

        log.info(kafkaMessage.obj().toString());
        switch (kafkaMessage.actionCode()) {
            case "append-url":
                KafkaAppendUrl appendUrl = objectMapper.convertValue(kafkaMessage.obj(), KafkaAppendUrl.class);
                Boolean appended = accountService.appendUrl(appendUrl.email(), appendUrl.url());
                if(!appended) {
                    log.error(FormatLogMessage.formatLogMessage(
                            this.getClass().getSimpleName(),
                            "kafkaMessage < appendUrl",
                            "Unexpected error: ",
                            "Email: "+appendUrl.email(), "Url: "+appendUrl.url()
                    ));
                }
                break;
            case "increase-traffic":
                KafkaIncreaseTraffic increaseTraffic = objectMapper.convertValue(kafkaMessage.obj(), KafkaIncreaseTraffic.class);
                Boolean increased = trafficService.increaseTraffic(increaseTraffic.shortCode(), increaseTraffic.trafficDate(), increaseTraffic.zoneId());
                if(!increased) {
                    log.error(FormatLogMessage.formatLogMessage(
                            this.getClass().getSimpleName(),
                            "kafkaMessage < increaseTraffic",
                            "Unexpected error: ",
                            "ShortCode: " + increaseTraffic.shortCode(),"TrafficDate: "+ increaseTraffic.trafficDate(), "ZoneId: "+ increaseTraffic.zoneId()
                    ));
                }
                break;
            default:
                break;
        }
    }

}
