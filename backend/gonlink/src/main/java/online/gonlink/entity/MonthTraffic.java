package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "month-traffic")
public class MonthTraffic implements Traffic{
    @Id
    private TrafficID id;
    private short[] trafficDays;

    public MonthTraffic(String shortCode, String trafficDate) {
        this.id = new TrafficID(shortCode, trafficDate);

//        String inputDate = "20/12/2001";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        // Chuyển đổi chuỗi ngày thành đối tượng LocalDate
//        LocalDate date = LocalDate.parse(inputDate, formatter);
//
//        // Lấy thông tin về năm và tháng
//        int year = date.getYear();
//        int month = date.getMonthValue();
//
//        // Tạo đối tượng YearMonth từ năm và tháng
//        YearMonth yearMonth = YearMonth.of(year, month);
//
//        // Lấy số ngày trong tháng
//        int daysInMonth = yearMonth.lengthOfMonth();
        this.trafficDays = new short[31];
    }

}
