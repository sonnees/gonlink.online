package online.gonlink.service;

import online.gonlink.dto.UserInfoDto;
import online.gonlink.entity.Account;

public interface AccountService {
    Account getInfoAccount(UserInfoDto userInfoDto);
}
