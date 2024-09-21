package online.gonlink.observer;

import online.gonlink.GetOriginalUrlRequest;
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
        this.increaseOsVersionClick(owner, request.getOsVersion());
        this.increaseDeviceTypeClick(owner, request.getDeviceType());
        this.increaseDeviceManufacturerClick(owner, request.getDeviceManufacturer());
        this.increaseDeviceNameClick(owner, request.getDeviceName());
        repository.increaseTotalClick(owner);
        return true;
    }

    @Override
    public void deletesTraffic(String shortCode) throws RuntimeException {}

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
    public long increaseOsVersionClick(String email, String osVersion) {
        long updatedCount = repository.increaseOsVersionClick(email, osVersion);
        if (updatedCount == 0) {
            return repository.insertNewOsVersionClick(email, osVersion);
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

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseDeviceManufacturerClick(String email, String deviceManufacturer) {
        long updatedCount = repository.increaseDeviceManufacturerClick(email, deviceManufacturer);
        if (updatedCount == 0) {
            return repository.insertNewDeviceManufacturerClick(email, deviceManufacturer);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseDeviceNameClick(String email, String deviceName) {
        long updatedCount = repository.increaseDeviceNameClick(email, deviceName);
        if (updatedCount == 0) {
            return repository.insertNewDeviceNameClick(email, deviceName);
        }
        return updatedCount;
    }
}
