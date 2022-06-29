package tcu.backend.cars.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cars")
@Getter
@Setter
@ToString
public class CarsServiceConfig {

    private String tokenValidTime;
}
