package online.gonlink.accountservice.service.base.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import online.gonlink.accountservice.dto.*;
import online.gonlink.accountservice.entity.ShortUrl;
import online.gonlink.accountservice.observer.CreateTrafficSubject;
import online.gonlink.accountservice.observer.GeneralTrafficObserver;
import online.gonlink.accountservice.observer.RealTimeTrafficObserver;
import online.gonlink.accountservice.service.AccountService;
import online.gonlink.accountservice.service.TrafficService;
import online.gonlink.accountservice.service.base.ConsumerService;
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
    private final CreateTrafficSubject createTrafficSubject;
    private final GeneralTrafficObserver generalTrafficObserver;
    private final RealTimeTrafficObserver realTimeTrafficObserver;
    private final AccountService accountService;
    private final TrafficService trafficService;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void initObservers() {
        createTrafficSubject.addObserver(generalTrafficObserver);
        createTrafficSubject.addObserver(realTimeTrafficObserver);
    }

    @Override
    @KafkaListener(topics = "${account-service.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        KafkaMessage kafkaMessage;
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

        switch (kafkaMessage.actionCode()) {
            case APPEND_URL:
                KafkaAppendUrl appendUrl = objectMapper.convertValue(kafkaMessage.obj(), KafkaAppendUrl.class);
                boolean appended = accountService.appendUrl(appendUrl.email(), new ShortUrl(appendUrl.shortCode(), appendUrl.originalUrl()));
                if(appended)
                    createTrafficSubject.create(new IncreaseTraffic(appendUrl.shortCode(), appendUrl.trafficDate(), appendUrl.zoneId()));
                else{
                    log.error(FormatLogMessage.formatLogMessage(
                            this.getClass().getSimpleName(),
                            "kafkaMessage < appendUrl",
                            "Unexpected error: ",
                            "Email: "+appendUrl.email(), "Url: "+appendUrl.shortCode()
                    ));
                }
                break;
            case ANONYMOUS_URL:
                KafkaAnonymousUrl anonymousUrl = objectMapper.convertValue(kafkaMessage.obj(), KafkaAnonymousUrl.class);
                    createTrafficSubject.create(new IncreaseTraffic(anonymousUrl.shortCode(), anonymousUrl.trafficDate(), anonymousUrl.zoneId()));
                break;
            case INCREASE_TRAFFIC:
                KafkaIncreaseTraffic increaseTraffic = objectMapper.convertValue(kafkaMessage.obj(), KafkaIncreaseTraffic.class);
                boolean increased = trafficService.increaseTraffic(increaseTraffic.shortCode(), increaseTraffic.trafficDate(), increaseTraffic.zoneId());
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
