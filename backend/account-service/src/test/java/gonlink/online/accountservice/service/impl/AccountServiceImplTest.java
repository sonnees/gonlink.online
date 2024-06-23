package gonlink.online.accountservice.service.impl;

import com.mongodb.DuplicateKeyException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcernResult;
import gonlink.online.accountservice.service.JsonConverter;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.bson.BsonDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gonlink.online.accountservice.entity.Account;
import gonlink.online.accountservice.repository.AccountRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountServiceImpl;
    @Mock
    AccountRepository accountRepository;
    @Mock
    JsonConverter jsonConverter;
    @Mock
    DuplicateKeyException duplicateKeyException;


    @Test
    void insert_OKE() {
        Account account = new Account(
                "demo@gmail.com",
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png"
        );

        when(accountRepository.insert(account)).thenReturn(account);
        Boolean insert = accountServiceImpl.insert(account.getEmail(), account.getName(), account.getAvatar());
        assertTrue(insert);

        verify(accountRepository).insert(account);
    }

    @Test
    void insert_ALREADY_EXISTS() {
        BsonDocument response = new BsonDocument();
        ServerAddress address = new ServerAddress("localhost", 27017);
        WriteConcernResult writeConcernResult = WriteConcernResult.unacknowledged();
        Account account = new Account(
                "demo@gmail.com",
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png"
        );
        when(accountRepository.insert(account)).thenThrow(new DuplicateKeyException(response, address, writeConcernResult));

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> accountServiceImpl.insert(account.getEmail(), account.getName(), account.getAvatar()));
        assertEquals(Status.ALREADY_EXISTS.getCode(), exception.getStatus().getCode());

        verify(accountRepository).insert(account);
    }

    @Test
    void insert_INTERNAL() {
        Account account = new Account(
                "demo@gmail.com",
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png"
        );
        when(accountRepository.insert(account)).thenThrow(new RuntimeException());

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> accountServiceImpl.insert(account.getEmail(), account.getName(), account.getAvatar()));
        assertEquals(Status.INTERNAL.getCode(), exception.getStatus().getCode());

        verify(accountRepository).insert(account);
    }

    @Test
    void appendUrl_OKE() {
        String email = "demo@gmail.com";
        String url = "12abCD";

        when(accountRepository.appendUrl(email, url)).thenReturn(1L);
        Boolean appendUrl = accountServiceImpl.appendUrl(email, url);
        assertTrue(appendUrl);

        verify(accountRepository).appendUrl(email, url);
    }

    @Test
    void appendUrl_NOT_FOUND() {
        String email = "demo@gmail.com";
        String url = "12abCD";

        when(accountRepository.appendUrl(email, url)).thenReturn(0L);
        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> accountServiceImpl.appendUrl(email, url));
        assertEquals(Status.NOT_FOUND.getCode(), exception.getStatus().getCode());

        verify(accountRepository).appendUrl(email, url);
    }

    @Test
    void appendUrl_INTERNAL() {
        String email = "demo@gmail.com";
        String url = "12abCD";

        when(accountRepository.appendUrl(email, url)).thenThrow(new RuntimeException());
        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> accountServiceImpl.appendUrl(email, url));
        assertEquals(Status.INTERNAL.getCode(), exception.getStatus().getCode());

        verify(accountRepository).appendUrl(email, url);
        verify(jsonConverter).objToString(any());
    }
}