package online.gonlink.service;

import online.gonlink.dto.UserInfo;
import online.gonlink.entity.Account;
import online.gonlink.entity.ShortUrl;

public interface AccountService {
    Account getInfoAccount(UserInfo userInfo);
    boolean appendUrl(String email, ShortUrl url);
    boolean removeUrl(String email, String shortCode);
}
