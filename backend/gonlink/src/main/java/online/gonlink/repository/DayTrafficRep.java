package online.gonlink.repository;

import online.gonlink.entity.DayTraffic;
import online.gonlink.entity.TrafficID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayTrafficRep extends MongoRepository<DayTraffic, TrafficID> {
    @Query("{id: ?0}")
    @Update("{ '$inc' : { 'trafficHours.?1' : 1 } }")
    long increaseTraffic(TrafficID id, int index);

    @Query(value = "{'id.shortCode': ?0}", delete = true)
    void deleteAllByShortCode(String shortCode);

    @Query(value = "{'id.shortCode': ?0, 'id.trafficDate': { $gte: ?1, $lte: ?2 }}")
    List<DayTraffic> findByShortCodeAndTrafficDate(String shortCode, String fromDate, String toDate);

    @Query("{ 'id': ?0, 'cities.name': ?1 }")
    @Update("{ '$inc': { 'cities.$.data': 1 } }")
    long increaseCityClick(TrafficID id, String city);
    @Query("{ 'id': ?0, 'cities.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'cities': { 'name': ?1, 'data': 1 } } }")
    long insertNewCityClick(TrafficID id, String city);

    @Query("{ 'id': ?0, 'countries.name': ?1 }")
    @Update("{ '$inc': { 'countries.$.data': 1 } }")
    long increaseCountryClick(TrafficID id, String country);
    @Query("{ 'id': ?0, 'countries.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'countries': { 'name': ?1, 'data': 1 } } }")
    long insertNewCountryClick(TrafficID id, String country);

    @Query("{ 'id': ?0, 'zoneIds.name': ?1 }")
    @Update("{ '$inc': { 'zoneIds.$.data': 1 } }")
    long increaseZoneIdClick(TrafficID id, String zoneId);
    @Query("{ 'id': ?0, 'zoneIds.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'zoneIds': { 'name': ?1, 'data': 1 } } }")
    long insertNewZoneIdClick(TrafficID id, String zoneId);

    @Query("{ 'id': ?0, 'browsers.name': ?1 }")
    @Update("{ '$inc': { 'browsers.$.data': 1 } }")
    long increaseBrowserClick(TrafficID id, String browserName);
    @Query("{ 'id': ?0, 'browsers.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'browsers': { 'name': ?1, 'data': 1 } } }")
    long insertNewBrowserClick(TrafficID id, String browserName);

    @Query("{ 'id': ?0, 'browserVersions.name': ?1 }")
    @Update("{ '$inc': { 'browserVersions.$.data': 1 } }")
    long increaseBrowserVersionClick(TrafficID id, String browserVersion);
    @Query("{ 'id': ?0, 'browserVersions.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'browserVersions': { 'name': ?1, 'data': 1 } } }")
    long insertNewBrowserVersionClick(TrafficID id, String browserVersion);

    @Query("{ 'id': ?0, 'operatingSystems.name': ?1 }")
    @Update("{ '$inc': { 'operatingSystems.$.data': 1 } }")
    long increaseOperatingSystemClick(TrafficID id, String operatingSystem);
    @Query("{ 'id': ?0, 'operatingSystems.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'operatingSystems': { 'name': ?1, 'data': 1 } } }")
    long insertNewOperatingSystemClick(TrafficID id, String operatingSystem);

    @Query("{ 'id': ?0, 'osVersions.name': ?1 }")
    @Update("{ '$inc': { 'osVersions.$.data': 1 } }")
    long increaseOsVersionClick(TrafficID id, String osVersion);
    @Query("{ 'id': ?0, 'osVersions.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'osVersions': { 'name': ?1, 'data': 1 } } }")
    long insertNewOsVersionClick(TrafficID id, String osVersion);

    @Query("{ 'id': ?0, 'deviceTypes.name': ?1 }")
    @Update("{ '$inc': { 'deviceTypes.$.data': 1 } }")
    long increaseDeviceTypeClick(TrafficID id, String deviceType);
    @Query("{ 'id': ?0, 'deviceTypes.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'deviceTypes': { 'name': ?1, 'data': 1 } } }")
    long insertNewDeviceTypeClick(TrafficID id, String deviceType);

    @Query("{ 'id': ?0, 'deviceManufacturers.name': ?1 }")
    @Update("{ '$inc': { 'deviceManufacturers.$.data': 1 } }")
    long increaseDeviceManufacturerClick(TrafficID id, String deviceManufacturer);
    @Query("{ 'id': ?0, 'deviceManufacturers.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'deviceManufacturers': { 'name': ?1, 'data': 1 } } }")
    long insertNewDeviceManufacturerClick(TrafficID id, String deviceManufacturer);

    @Query("{ 'id': ?0, 'deviceNames.name': ?1 }")
    @Update("{ '$inc': { 'deviceNames.$.data': 1 } }")
    long increaseDeviceNameClick(TrafficID id, String deviceName);
    @Query("{ 'id': ?0, 'deviceNames.name': { '$ne': ?1 } }")
    @Update("{ '$push': { 'deviceNames': { 'name': ?1, 'data': 1 } } }")
    long insertNewDeviceNameClick(TrafficID id, String deviceName);
}
