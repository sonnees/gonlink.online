package gonlink.online.accountservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class AccountServiceApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime localDate= LocalDateTime.now();
        LocalDateTime truncatedDate = localDate.truncatedTo(ChronoUnit.HOURS);

        System.out.println(truncatedDate);
    }

}
