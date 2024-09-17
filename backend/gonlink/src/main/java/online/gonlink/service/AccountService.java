package online.gonlink.service;

import online.gonlink.GetInfoAccountRequest;
import online.gonlink.GetInfoAccountResponse;

public interface AccountService {
    GetInfoAccountResponse getInfoAccount(GetInfoAccountRequest request);
}
