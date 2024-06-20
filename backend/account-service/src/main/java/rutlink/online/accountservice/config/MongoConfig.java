package rutlink.online.accountservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import rutlink.online.accountservice.service.DateToZonedDateTimeConverter;
import rutlink.online.accountservice.service.ZonedDateTimeToDateConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeToDateConverter());
        converters.add(new DateToZonedDateTimeConverter());
        return new MongoCustomConversions(converters);
    }
}
