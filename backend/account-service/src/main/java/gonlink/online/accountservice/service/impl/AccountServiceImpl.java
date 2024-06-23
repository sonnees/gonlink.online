package gonlink.online.accountservice.service.impl;

import com.mongodb.DuplicateKeyException;
import gonlink.online.accountservice.entity.Account;
import gonlink.online.accountservice.service.JsonConverter;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import gonlink.online.accountservice.dto.LogObj;
import gonlink.online.accountservice.dto.error.AppendUrl;
import gonlink.online.accountservice.repository.AccountRepository;
import gonlink.online.accountservice.service.AccountService;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final JsonConverter jsonConverter;

    @Override
    public Boolean insert(String email, String name, String avatar) {
        Account account = new Account(email, name, avatar);
        try {
            accountRepository.insert(account);
            return true;
        } catch (DuplicateKeyException e){
            throw new StatusRuntimeException( Status.ALREADY_EXISTS.withDescription("Duplicate Key Error"));
        } catch (Exception e){
            log.error("@ {}", jsonConverter.objToString(new LogObj<>(this.getClass(), "insert", account)));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
        }
    }

    @Override
    public Boolean appendUrl(String email, String url) {
        try {
            Long appendUrl = accountRepository.appendUrl(email, url);
            if(appendUrl>0) return true;
            else throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("Account Not Found"));
        }
        catch (StatusRuntimeException e){
            throw e;
        }
        catch (Exception e){
            log.error("@ {}", jsonConverter.objToString(new LogObj<>(this.getClass(),"appendUrl", new AppendUrl(email, url))));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
        }
    }
}
