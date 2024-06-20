package rutlink.online.accountservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rutlink.online.accountservice.entity.Traffic;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrafficRepositoryTest {

    @Autowired
    TrafficRepository trafficRepository;

    @Test
    void save() {
        String shortCode = "abcdef";
        Traffic traffic = new Traffic(shortCode);

        Traffic save = trafficRepository.save(traffic);

        assertNotNull(save);
    }

    @Test
    void findTrafficByShortCodeAndTrafficDate() {
    }

    @Test
    void addValueToTrafficHour() {
    }
}