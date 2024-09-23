package online.gonlink.util;

import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.exception.ResourceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER_YYYY_MM_DD_HH = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    public static int getDaysInDate(String trafficDate) {
        int daysInMonth;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(trafficDate+"-01", formatter);
            int year = date.getYear();
            int month = date.getMonthValue();
            YearMonth yearMonth = YearMonth.of(year, month);
            daysInMonth = yearMonth.lengthOfMonth();
        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.DATE_FORMAT.name(), e);
        }
        return daysInMonth;
    }

    public static ZonedDateTime getZonedDateTime(String date, String zoneID) {
        return ZonedDateTime.parse(date).withZoneSameInstant(ZoneId.of(zoneID));
    }

    public static ZonedDateTime getZonedDateTime(String date, DateTimeFormatter formatter, String zoneID) {
        return LocalDateTime.parse(date, formatter)
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of(zoneID));
    }

    public static ZonedDateTime getZonedDateTime(String date, DateTimeFormatter formatter, ZoneId zoneID) {
        return LocalDateTime.parse(date, formatter)
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneID);
    }

    public static String getStringZonedDateTime(String date, DateTimeFormatter formatter, String zoneID) {
        return getZonedDateTime(date, formatter, zoneID)
                .format(formatter);
    }

    public static String getStringZonedDateTime(String date, DateTimeFormatter formatter, ZoneId zoneID) {
        return getZonedDateTime(date, formatter, zoneID)
                .format(formatter);
    }

    public static int getDayInMonthFromDateFormatter(String date, DateTimeFormatter formatter, String zoneID) {
        return getZonedDateTime(date, formatter, zoneID)
                .getDayOfMonth();
    }

    public static int getHourFromDateFormatter(String date, DateTimeFormatter formatter, ZoneId zoneID) {
        return getZonedDateTime(date, formatter, zoneID)
                .getHour();
    }

    public static int getDayInMonthFromDateFormatter(String date, DateTimeFormatter formatter, ZoneId zoneID) {
        return getZonedDateTime(date, formatter, zoneID)
                .getDayOfMonth();
    }

    public static int getMonthFromDateFormatter(String date, DateTimeFormatter formatter, String zoneID) {
        return getZonedDateTime(date, formatter, zoneID)
                .getMonthValue();
    }

    public static int getMonthFromDateFormatter(String date, DateTimeFormatter formatter, ZoneId zoneID) {
        return getZonedDateTime(date, formatter, zoneID)
                .getMonthValue();
    }

    public static String getCusFormatFromLocalDate(LocalDate localDate) {
        return localDate.getDayOfMonth()+" thg "
                + localDate.getMonthValue() + ", "
                + localDate.getYear();
    }

    public static LocalDate getLocalDateForZoneID(LocalDate localDate, ZoneId zoneID) {
        return LocalDateTime.of(localDate, LocalTime.MIN)
                .atZone(ZoneId.of("UTC"))
                .withZoneSameLocal(zoneID)
                .toLocalDate();
    }

    public static boolean isBeforeLocalDateTime(String timeString1, String timeString2) {
        LocalDateTime time1 = LocalDateTime.parse(timeString1, FORMATTER_YYYY_MM_DD_HH);
        LocalDateTime time2 = LocalDateTime.parse(timeString2, FORMATTER_YYYY_MM_DD_HH);
        return !time1.isAfter(time2);
    }

}
