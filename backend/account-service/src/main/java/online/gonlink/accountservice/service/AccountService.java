package online.gonlink.accountservice.service;

import online.gonlink.accountservice.dto.UserInfo;
import online.gonlink.accountservice.entity.Account;

public interface AccountService {
    Account getInfoAccount(UserInfo userInfo);
    Boolean appendUrl(String email, String url);
}
