package rutlink.online.shortenservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CheckURL {
    @Value("${shorten-service.URL_FORBIDDEN}")
    private String[] URL_FORBIDDEN;

    public boolean isExits(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");

        int responseCode = connection.getResponseCode();
        return responseCode == HttpURLConnection.HTTP_OK;
    }

    public boolean isNotForbidden(String urlString){
        for (String forbiddenUrl : URL_FORBIDDEN)
            if (urlString.contains(forbiddenUrl)) return false;
        return true;
    }



}
