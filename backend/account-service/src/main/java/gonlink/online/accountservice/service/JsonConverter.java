package gonlink.online.accountservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JsonConverter {
    private final ObjectMapper objectMapper;

    public <T> String objToString(T obj) {
        String objStr = null;
        try {
            objStr = objectMapper.writeValueAsString(obj);
        } catch (Exception e){
            return e.getMessage();
        }
        return objStr;
    }
}
