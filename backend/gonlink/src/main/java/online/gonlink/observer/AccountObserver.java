package online.gonlink.observer;

import io.grpc.Context;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.constant.AuthConstant;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.repository.AccountRep;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountObserver implements TrafficObserver{
    private final AccountRep repository;

    public AccountObserver(AccountRep repository){
        this.repository = repository;
    }

    @Override
    public boolean increasesTraffic(String owner, String originalUrl, GetOriginalUrlRequest request) throws RuntimeException {
        this.increaseCityClick(owner, "Ho Chi Minh");
        this.increaseCountryClick(owner, "VietNam");
        this.increaseZoneIdClick(owner, request.getZoneId());
        this.increaseBrowserClick(owner, request.getBrowser());
        this.increaseBrowserVersionClick(owner, request.getBrowserVersion());
        this.increaseOperatingSystemClick(owner, request.getOperatingSystem());
        this.increaseDeviceTypeClick(owner, request.getDeviceType());
        repository.increaseTotalClick(owner);
        return true;
    }

    @Override
    public void deletesTraffic(String shortCode) throws RuntimeException {
        repository.minusTotalShortURL(AuthConstant.USER_EMAIL.get(Context.current()));
    }

    @Override
    public boolean createsTraffic(TrafficCreateDto record) throws RuntimeException {
        repository.increaseTotalShortURL(record.owner());
        return true;
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseCityClick(String email, String city) {
        long updatedCount = repository.increaseCityClick(email, city);
        if (updatedCount == 0) {
            return repository.insertNewCityClick(email, city);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseCountryClick(String email, String country) {
        long updatedCount = repository.increaseCountryClick(email, country);
        if (updatedCount == 0) {
            return repository.insertNewCountryClick(email, country);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseZoneIdClick(String email, String timezone) {
        long updatedCount = repository.increaseZoneIdClick(email, timezone);
        if (updatedCount == 0) {
            return repository.insertNewZoneIdClick(email, timezone);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseBrowserClick(String email, String browserName) {
        long updatedCount = repository.increaseBrowserClick(email, browserName);
        if (updatedCount == 0) {
            return repository.insertNewBrowserClick(email, browserName);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseBrowserVersionClick(String email, String browserVersion) {
        long updatedCount = repository.increaseBrowserVersionClick(email, browserVersion);
        if (updatedCount == 0) {
            return repository.insertNewBrowserVersionClick(email, browserVersion);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseOperatingSystemClick(String email, String operatingSystem) {
        long updatedCount = repository.increaseOperatingSystemClick(email, operatingSystem);
        if (updatedCount == 0) {
            return repository.insertNewOperatingSystemClick(email, operatingSystem);
        }
        return updatedCount;
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseDeviceTypeClick(String email, String deviceType) {
        long updatedCount = repository.increaseDeviceTypeClick(email, deviceType);
        if (updatedCount == 0) {
            return repository.insertNewDeviceTypeClick(email, deviceType);
        }
        return updatedCount;
    }
}
