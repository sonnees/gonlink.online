package rutlink.online.shortenservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonConverter {
    private final ObjectMapper objectMapper;

    public JsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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
