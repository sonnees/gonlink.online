package online.gonlink.accountservice.service.impl;

import online.gonlink.accountservice.dto.UserInfo;
import online.gonlink.accountservice.entity.Account;
import online.gonlink.accountservice.entity.ShortUrl;
import online.gonlink.accountservice.exception.GrpcStatusException;
import online.gonlink.accountservice.util.FormatLogMessage;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import online.gonlink.accountservice.repository.AccountRepository;
import online.gonlink.accountservice.service.AccountService;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account getInfoAccount(UserInfo userInfo) {
        try {
            Optional<Account> account = accountRepository.findById(userInfo.user_email());
            return account.orElseGet(() -> accountRepository.insert(
                    new Account(userInfo)
            ));
        }catch (Exception e){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "getInfoAccount",
                    "Unexpected error: {}",
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
        }
    }

    @Override
    public Boolean appendUrl(String email, ShortUrl shortUrl) {
        try {
            Long appendUrl = accountRepository.appendUrl(email, shortUrl);
            if(appendUrl>0) return true;
            else throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("Account Not Found"));
        }
        catch (StatusRuntimeException e){
            throw new GrpcStatusException(e);
        }
        catch (Exception e){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "appendUrl",
                    "Unexpected error: {}",
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
        }
    }
}
