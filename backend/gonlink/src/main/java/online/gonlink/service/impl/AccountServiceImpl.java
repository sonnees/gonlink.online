package online.gonlink.service.impl;

import online.gonlink.dto.Standard;
import online.gonlink.dto.StandardResponseGrpc;
import online.gonlink.dto.UserInfo;
import online.gonlink.entity.Account;
import online.gonlink.entity.ShortUrl;
import online.gonlink.exception.ResourceException;
import lombok.AllArgsConstructor;
import online.gonlink.repository.AccountRepository;
import org.springframework.stereotype.Service;
import online.gonlink.service.AccountService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final StandardResponseGrpc standardResponseGrpc;

    @Override
    public Account getInfoAccount(UserInfo userInfo) {
        Optional<Account> account = accountRepository.findById(userInfo.user_email());
        return account.orElseGet(
                () -> accountRepository.insert(new Account(userInfo))
        );
    }

    @Override
    public boolean appendUrl(String email, ShortUrl shortUrl) {
        long appendUrl = accountRepository.appendUrl(email, shortUrl);
        if(appendUrl <= 0)
            throw new ResourceException(standardResponseGrpc.standardResponseJson(Standard.ACCOUNT_APPEND_URL));
        return true;
    }

    @Override
    public boolean removeUrl(String email, String shortCode) {
        long appendUrl = accountRepository.removeUrl(email, shortCode);
        if(appendUrl <= 0)
            throw new ResourceException(standardResponseGrpc.standardResponseJson(Standard.ACCOUNT_REMOVE_URL_FAIL));
        return true;
    }
}
