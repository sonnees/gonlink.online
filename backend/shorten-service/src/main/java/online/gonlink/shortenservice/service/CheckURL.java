package online.gonlink.shortenservice.service;

import lombok.AllArgsConstructor;
import online.gonlink.shortenservice.config.AccountServiceConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@AllArgsConstructor
public class CheckURL {
    private AccountServiceConfig config;

    public boolean isExits(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");

        int responseCode = connection.getResponseCode();
        return responseCode == HttpURLConnection.HTTP_OK;
    }

    public boolean isNotForbidden(String urlString){
        for (String forbiddenUrl : config.getURL_FORBIDDEN())
            if (urlString.contains(forbiddenUrl)) return false;
        return true;
    }

}
