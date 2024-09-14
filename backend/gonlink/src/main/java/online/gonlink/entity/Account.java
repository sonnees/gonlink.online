package online.gonlink.entity;

import online.gonlink.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
public class Account {
    @Id
    private String email;
    private String name;
    private String avatar;
    private String role;
    private LocalDate create;

    public Account(UserInfoDto userInfoDto) {
        this.email = userInfoDto.user_email();
        this.name = userInfoDto.user_name();
        this.avatar = userInfoDto.user_avatar();
        this.role = userInfoDto.user_role();
        this.create = LocalDate.now();
    }
    public String getCreate() {
        return this.create.toString();
    }
}
