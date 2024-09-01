package online.gonlink.entity;

import online.gonlink.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.*;

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
    private List<ShortUrl> urls;

    public Account(UserInfo userInfo) {
        this.email = userInfo.user_email();
        this.name = userInfo.user_name();
        this.avatar = userInfo.user_avatar();
        this.role = userInfo.user_role();
        this.create = LocalDate.now();
        this.urls = new ArrayList<>();
    }
    public String getCreate() {
        return this.create.toString();
    }
}
