package online.gonlink.util;

import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.exception.ResourceException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
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
}
