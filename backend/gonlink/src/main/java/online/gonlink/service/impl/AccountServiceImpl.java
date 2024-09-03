package online.gonlink.service.impl;

import online.gonlink.dto.StandardResponseGrpc;
import online.gonlink.dto.UserInfo;
import online.gonlink.entity.Account;
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
}
