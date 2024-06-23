package gonlink.online.accountservice.service;

public interface AccountService {
    Boolean insert(String email, String name, String avatar);
    Boolean appendUrl(String email, String url);
}
