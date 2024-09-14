package online.gonlink.util;

import lombok.AllArgsConstructor;
import online.gonlink.config.GlobalValue;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.exception.ResourceException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@AllArgsConstructor
public class CheckURL {
    private GlobalValue config;

    public void isExits(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new ResourceException(ExceptionEnum.NOT_FOUND_URL.name(), null);
        } catch (IOException e){
            throw new ResourceException(ExceptionEnum.NOT_FOUND_URL.name(), e);
        }
    }

    public void isNotForbidden(String urlString){
        for (String forbiddenUrl : config.getURL_FORBIDDEN())
            if (urlString.contains(forbiddenUrl))
                throw new ResourceException(ExceptionEnum.FORBIDDEN_URL.name(), null);
    }

}
