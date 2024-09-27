package online.gonlink.repository;

import online.gonlink.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRep extends MongoRepository<Account, String> {
    @Query("{email: ?0}")
    @Update("{'$inc': {totalShortURL: 1}}")
    long increaseTotalShortURL(String email);

    @Query("{email: ?0}")
    @Update("{'$inc': {totalShortURL: -1}}")
    long minusTotalShortURL(String email);

    @Query("{email: ?0}")
    @Update("{'$inc': {totalClick: 1}}")
    long increaseTotalClick(String email);

    @Query("{ 'email': ?0, 'cities.name': ?1 }")
    @Update("{ '$inc': { 'cities.$.data': 1 } }")
    long increaseCityClick(String email, String city);
    @Query("{ 'email': ?0, 'cities.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'cities': { 'name': ?1, 'data': 1 } } }")
    long insertNewCityClick(String email, String city);

    @Query("{ 'email': ?0, 'countries.name': ?1 }")
    @Update("{ '$inc': { 'countries.$.data': 1 } }")
    long increaseCountryClick(String email, String country);
    @Query("{ 'email': ?0, 'countries.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'countries': { 'name': ?1, 'data': 1 } } }")
    long insertNewCountryClick(String email, String country);

    @Query("{ 'email': ?0, 'zoneIds.name': ?1 }")
    @Update("{ '$inc': { 'zoneIds.$.data': 1 } }")
    long increaseZoneIdClick(String email, String zoneId);
    @Query("{ 'email': ?0, 'zoneIds.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'zoneIds': { 'name': ?1, 'data': 1 } } }")
    long insertNewZoneIdClick(String email, String zoneId);

    @Query("{ 'email': ?0, 'browsers.name': ?1 }")
    @Update("{ '$inc': { 'browsers.$.data': 1 } }")
    long increaseBrowserClick(String email, String browserName);
    @Query("{ 'email': ?0, 'browsers.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'browsers': { 'name': ?1, 'data': 1 } } }")
    long insertNewBrowserClick(String email, String browserName);

    @Query("{ 'email': ?0, 'browserVersions.name': ?1 }")
    @Update("{ '$inc': { 'browserVersions.$.data': 1 } }")
    long increaseBrowserVersionClick(String email, String browserVersion);
    @Query("{ 'email': ?0, 'browserVersions.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'browserVersions': { 'name': ?1, 'data': 1 } } }")
    long insertNewBrowserVersionClick(String email, String browserVersion);

    @Query("{ 'email': ?0, 'operatingSystems.name': ?1 }")
    @Update("{ '$inc': { 'operatingSystems.$.data': 1 } }")
    long increaseOperatingSystemClick(String email, String operatingSystem);
    @Query("{ 'email': ?0, 'operatingSystems.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'operatingSystems': { 'name': ?1, 'data': 1 } } }")
    long insertNewOperatingSystemClick(String email, String operatingSystem);

    @Query("{ 'email': ?0, 'deviceTypes.name': ?1 }")
    @Update("{ '$inc': { 'deviceTypes.$.data': 1 } }")
    long increaseDeviceTypeClick(String email, String deviceType);
    @Query("{ 'email': ?0, 'deviceTypes.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'deviceTypes': { 'name': ?1, 'data': 1 } } }")
    long insertNewDeviceTypeClick(String email, String deviceType);
}
