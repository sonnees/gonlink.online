package online.gonlink.service.impl;

import online.gonlink.dto.UserInfoDto;
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

    @Override
    public Account getInfoAccount(UserInfoDto userInfoDto) {
        Optional<Account> account = accountRepository.findById(userInfoDto.user_email());
        return account.orElseGet(
                () -> accountRepository.insert(new Account(userInfoDto))
        );
    }
}
