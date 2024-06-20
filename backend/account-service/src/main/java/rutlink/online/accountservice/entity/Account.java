package rutlink.online.accountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
public class Account {
    @Id
    private String email;
    private String name;
    private String avatar;
    private LocalDate create;
    private Set<String> urls;

    public Account(String email, String name, String avatar) {
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.create = LocalDate.now();
        this.urls = new HashSet<>();
    }
}
