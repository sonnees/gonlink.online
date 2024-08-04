package online.gonlink.accountservice.factory;

import online.gonlink.accountservice.entity.*;
import online.gonlink.accountservice.factory.type.TrafficType;
import org.springframework.stereotype.Component;

public class TrafficFactory {
    public static Traffic createTraffic(TrafficType type, Object... params) {
        switch (type) {
            case GENERAL:
                try {
                    if(
                            params.length == 2
                            && params[0] instanceof String
                            && params[1] instanceof String
                    ){
                        return new GeneralTraffic(
                                (String) params[0],
                                (String) params[1]
                        );
                    }
                    throw new IllegalArgumentException("Error params");
                } catch (Exception e){
                    throw new IllegalArgumentException("Error creating: "+ e.getMessage(), e);
                }

            case MONTH:
                try {
                    if(
                            params.length == 2
                                    && params[0] instanceof String
                                    && params[1] instanceof String
                    ){
                        return new MonthTraffic(
                                (String) params[0],
                                (String) params[1]
                        );
                    }
                    throw new IllegalArgumentException("Error params");
                } catch (Exception e){
                    throw new IllegalArgumentException("Error creating: "+ e.getMessage(), e);
                }

            case DAY:
                try {
                    if(
                            params.length == 2
                                    && params[0] instanceof String
                                    && params[1] instanceof String
                    ){
                        return new DayTraffic(
                                (String) params[0],
                                (String) params[1]
                        );
                    }
                    throw new IllegalArgumentException("Error params");
                } catch (Exception e){
                    throw new IllegalArgumentException("Error creating: "+ e.getMessage(), e);
                }

            case REAL_TIME:
                try {
                    if(
                            params.length == 2
                                    && params[0] instanceof String
                                    && params[1] instanceof String
                    ){
                        return new RealTimeTraffic(
                                (String) params[0],
                                (String) params[1]
                        );
                    }
                    throw new IllegalArgumentException("Error params");
                } catch (Exception e){
                    throw new IllegalArgumentException("Error creating: "+ e.getMessage(), e);
                }

            default:
                throw new IllegalArgumentException("Error creating: ? default ?");
        }
    }

}
