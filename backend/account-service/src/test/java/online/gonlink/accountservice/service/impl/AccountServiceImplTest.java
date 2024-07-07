package online.gonlink.accountservice.service.impl;

import online.gonlink.accountservice.dto.UserInfo;
import online.gonlink.accountservice.exception.GrpcStatusException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import online.gonlink.accountservice.entity.Account;
import online.gonlink.accountservice.repository.AccountRepository;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @InjectMocks AccountServiceImpl accountServiceImpl;
    @Mock AccountRepository accountRepository;

    @Test
    void getInfoAccount_EXISTS() {
        UserInfo userInfo = new UserInfo(
                "demo@gmail.com",
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png",
                "user"
        );
        Account account = new Account(userInfo);

        when(accountRepository.findById(userInfo.user_email())).thenReturn(Optional.of(account));

        Account result = accountServiceImpl.getInfoAccount(userInfo);

        assertEquals(account, result);

        verify(accountRepository).findById(userInfo.user_email());
    }

    @Test
    void getInfoAccount_NOT_EXISTS() {
        UserInfo userInfo = new UserInfo(
                "demo@gmail.com",
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png",
                "user"
        );
        Account account = new Account(userInfo);

        when(accountRepository.findById(userInfo.user_email())).thenReturn(Optional.empty());
        when(accountRepository.insert(account)).thenReturn(account);

        Account result = accountServiceImpl.getInfoAccount(userInfo);

        assertEquals(account, result);

        verify(accountRepository).findById(userInfo.user_email());
        verify(accountRepository).insert(account);
    }

    @Test
    void getInfoAccount_INTERNAL() {
        UserInfo userInfo = new UserInfo(
                "demo@gmail.com",
                "demo",
                "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png",
                "user"
        );
        Account account = new Account(userInfo);

        when(accountRepository.findById(userInfo.user_email())).thenReturn(Optional.empty());
        when(accountRepository.insert(account)).thenThrow(new RuntimeException());

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> accountServiceImpl.getInfoAccount(userInfo));

        assertEquals(Status.INTERNAL.getCode(), exception.getStatus().getCode());

        verify(accountRepository).findById(userInfo.user_email());
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
        GrpcStatusException exception = assertThrows(GrpcStatusException.class, () -> accountServiceImpl.appendUrl(email, url));
        assertEquals(Status.NOT_FOUND.getCode(), exception.getStatusException().getStatus().getCode());

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
    }
}