package online.gonlink.accountservice.service;

import online.gonlink.accountservice.dto.UserInfo;
import online.gonlink.accountservice.entity.Account;
import online.gonlink.accountservice.entity.ShortUrl;

public interface AccountService {
    Account getInfoAccount(UserInfo userInfo);
    boolean appendUrl(String email, ShortUrl url);
    boolean removeUrl(String email, String shortCode);
}
