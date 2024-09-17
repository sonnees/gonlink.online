package online.gonlink.service.base.impl;


import io.grpc.Context;
import lombok.AllArgsConstructor;
import online.gonlink.config.GlobalValue;
import online.gonlink.constant.AuthConstant;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@AllArgsConstructor
public class IPGeolocationServiceImpl{
    private final GlobalValue config;

    public void get(){
        try {
            String ip = AuthConstant.IP_ADDRESS.get(Context.current());
            URL url = new URL("https://ip-api.com/json/" + ip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String s = response.toString();

//            System.out.println("Quốc gia: " + jsonResponse.getString("country"));
//            System.out.println("Thành phố: " + jsonResponse.getString("city"));
//            System.out.println("Vĩ độ: " + jsonResponse.getDouble("lat"));
//            System.out.println("Kinh độ: " + jsonResponse.getDouble("lon"));
//            System.out.println("Múi giờ: " + jsonResponse.getString("timezone"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
