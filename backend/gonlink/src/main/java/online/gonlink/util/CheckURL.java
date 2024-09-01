package online.gonlink.util;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import online.gonlink.config.GlobalValue;
import online.gonlink.exception.GrpcStatusException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@AllArgsConstructor
public class CheckURL {
    private GlobalValue config;

    public boolean isExits(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (MalformedURLException e){
            throw new GrpcStatusException(new StatusRuntimeException(Status.NOT_FOUND.withDescription("URL Not Found")));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isNotForbidden(String urlString){
        for (String forbiddenUrl : config.getURL_FORBIDDEN())
            if (urlString.contains(forbiddenUrl)) return false;
        return true;
    }

}
