package rutlink.online.accountservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rutlink.online.accountservice.entity.Account;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    void save(){
        Account account = new Account(
                "son@gmail.com",
                "son nees",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png"
        );
        Account save = accountRepository.save(account);

        assertNotNull(save);
    }

    @Test
    void appendUrl(){
        String email = "son@gmail.com";
        String url = "abcdef";
        Long aLong = accountRepository.appendUrl(email, url);

        assertTrue(aLong>=0);
    }
}