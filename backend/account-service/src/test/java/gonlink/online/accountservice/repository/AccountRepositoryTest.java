package gonlink.online.accountservice.repository;

import gonlink.online.accountservice.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    void insert(){
        Account account = new Account(
                "demo@gmail.com",
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png"
        );
        Account insert = accountRepository.insert(account);

        assertNotNull(insert);
        accountRepository.delete(insert);
    }

    @Test
    void appendUrl(){
        String email = "demo@gmail.com";
        String url = "12abCD";
        insert_(email);

        Long aLong = accountRepository.appendUrl(email, url);
        assertTrue(aLong>0);
        accountRepository.deleteById(email);
    }

    @Test
    void appendUrl_NOT_FOUND(){
        String email = "demo@gmail.com";
        String emailTemp = "temp@gmail.com";
        String url = "12abCD";
        insert_(email);

        Long aLong = accountRepository.appendUrl(emailTemp, url);
        assertTrue(aLong<=0);
        accountRepository.deleteById(email);
    }

    void insert_(String email){
        Account account = new Account(
                email,
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png"
        );
        Account insert = accountRepository.insert(account);
        assertNotNull(insert);
    }
}