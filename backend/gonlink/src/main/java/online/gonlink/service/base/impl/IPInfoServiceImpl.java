package online.gonlink.service.base.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import online.gonlink.config.GlobalValue;
import online.gonlink.dto.IpInfoDto;
import online.gonlink.exception.ResourceException;
import online.gonlink.exception.enumdef.ExceptionEnum;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
@AllArgsConstructor
public class IPInfoServiceImpl {
    private final GlobalValue config;
    private final ObjectMapper objectMapper;

    public IpInfoDto get(String ip){
        HttpURLConnection conn = null;
        try{
            if(ip.equals("::1")) ip = config.getDEFAULT_IP();

            String apiUrl = String.format("https://ipinfo.io/%s/json?token=%s", ip, config.getIP_IPINFO_KEY());
            URL url = new URL(apiUrl);
            conn =  (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200)
                throw new ResourceException(ExceptionEnum.IP_ERROR.name(), null);

            return objectMapper.readValue(conn.getInputStream(), IpInfoDto.class);
        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.IP_ERROR.name(), null);
        }
    }
}
