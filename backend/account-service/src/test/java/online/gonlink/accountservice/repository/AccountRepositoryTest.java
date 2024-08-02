package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.dto.UserInfo;
import online.gonlink.accountservice.entity.Account;
import online.gonlink.accountservice.entity.ShortUrl;
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
        UserInfo userInfo = new UserInfo(
                "demo@gmail.com",
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png",
                "user"
        );
        Account account = new Account(userInfo);
        Account insert = accountRepository.insert(account);

        assertNotNull(insert);
        accountRepository.delete(insert);
    }

    @Test
    void appendUrl(){
        String email = "demo@gmail.com";
        String url = "12abCD";
        String originalUrl = "https://github.com/sonnees";
        ShortUrl shortUrl = new ShortUrl(url, originalUrl);
        insert_(email);

        Long aLong = accountRepository.appendUrl(email, shortUrl);
        assertTrue(aLong>0);
        accountRepository.deleteById(email);
    }

    @Test
    void appendUrl_NOT_FOUND(){
        String email = "demo@gmail.com";
        String emailTemp = "temp@gmail.com";
        String url = "12abCD";
        String originalUrl = "https://github.com/sonnees";
        ShortUrl shortUrl = new ShortUrl(url, originalUrl);
        insert_(email);

        Long aLong = accountRepository.appendUrl(emailTemp, shortUrl);
        assertTrue(aLong<=0);
        accountRepository.deleteById(email);
    }

    void insert_(String email){
        UserInfo userInfo = new UserInfo(
                email,
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png",
                "user"
        );
        Account account = new Account(userInfo);
        Account insert = accountRepository.insert(account);
        assertNotNull(insert);
    }
}