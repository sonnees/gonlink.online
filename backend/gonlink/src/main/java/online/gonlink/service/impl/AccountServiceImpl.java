package online.gonlink.service.impl;

import io.grpc.Context;
import online.gonlink.DataClient;
import online.gonlink.GetInfoAccountRequest;
import online.gonlink.GetInfoAccountResponse;
import online.gonlink.constant.AuthConstant;
import online.gonlink.entity.Account;
import lombok.AllArgsConstructor;
import online.gonlink.repository.AccountRep;
import org.springframework.stereotype.Service;
import online.gonlink.service.AccountService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    /** Repository */
    private final AccountRep accountRep;

    @Override
    public GetInfoAccountResponse getInfoAccount(GetInfoAccountRequest request){
        Optional<Account> accountOptional = accountRep.findById(AuthConstant.USER_EMAIL.get(Context.current()));
        Account account = accountOptional.orElseGet(
                () -> {
                    Account accountInsert = new Account();
                    accountInsert.setEmail(AuthConstant.USER_EMAIL.get(Context.current()));
                    accountInsert.setName(AuthConstant.USER_NAME.get(Context.current()));
                    accountInsert.setRole(AuthConstant.USER_ROLE.get(Context.current()));
                    accountInsert.setAvatar(AuthConstant.USER_AVATAR.get(Context.current()));
                    return accountRep.insert(accountInsert);
                });
        return GetInfoAccountResponse.newBuilder()
                .setEmail(account.getEmail())
                .setName(account.getName())
                .setAvatar(account.getAvatar())
                .setRole(account.getRole())
                .setCreate(account.getCreate())
                .setTotalShortURL(account.getTotalShortURL())
                .setTotalClick(account.getTotalClick())
                .addAllCities(account.getCities().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .addAllCountries(account.getCountries().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .addAllZoneIds(account.getZoneIds().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .addAllBrowsers(account.getBrowsers().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .addAllBrowserVersions(account.getBrowserVersions().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .addAllOperatingSystems(account.getOperatingSystems().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .addAllOsVersions(account.getOsVersions().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .addAllDeviceTypes(account.getDeviceTypes().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .addAllDeviceManufacturers(account.getDeviceManufacturers().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .addAllDeviceNames(account.getDeviceNames().stream().map(i -> DataClient.newBuilder().setName(i.getName()).setData(i.getData()).build()).collect(Collectors.toList()))
                .build();
    }
}
