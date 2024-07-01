package online.gonlink.accountservice.service.base;

import online.gonlink.accountservice.dto.KafkaMessage;

public interface ProducerService {
    public void sendMessage(KafkaMessage message);
}
