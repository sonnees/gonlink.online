package online.gonlink.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Any;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import online.gonlink.StandardResponse;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StandardResponseGrpc {
    ObjectMapper objectMapper;

    public StandardResponse standardResponse(Standard standard){
        return StandardResponse
                .newBuilder()
                .setMessage(standard.message)
                .setData(Any.pack(standard.data))
                .build();
    }

    public String standardResponseJson(Standard standard){
        try {
            return objectMapper.writeValueAsString(
                    StandardResponse
                            .newBuilder()
                            .setMessage(standard.message)
                            .setData(Any.pack(standard.data))
                            .build()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // bat loi
        }
    }

}
