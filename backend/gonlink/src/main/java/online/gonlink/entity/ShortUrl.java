package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import online.gonlink.constant.CommonConstant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = CommonConstant.COLLECTION_SHORT_URL)
public class ShortUrl {
    @Id
    private String shortCode;
    private String originalUrl;
    private String owner;
    private boolean active;

    private String alias;
    private String desc;
    private LocalDateTime timeExpired;
    private String password;
    private long maxUsage;
}