//package gonlink.online.shortenservice.service.base;
//
//import dto.online.gonlink.shortenservice.KafkaMessage;
//import dto.online.gonlink.shortenservice.KafkaSendAppendUrl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.annotation.EnableKafka;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@EnableKafka
//class ProducerServiceTest {
//    @Autowired ProducerService producerService;
//
//    @Test
//    void sendMessage() {
//        KafkaMessage message = new KafkaMessage("01",new KafkaSendAppendUrl("s","link"));
//        producerService.sendMessage(message);
//    }
//}