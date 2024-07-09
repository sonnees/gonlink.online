package online.gonlink.shortenservice.service.base;

import online.gonlink.shortenservice.dto.KafkaMessage;

public interface ProducerService {
    public void sendMessage(KafkaMessage message);
}
