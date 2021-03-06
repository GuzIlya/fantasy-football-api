package by.guz.fantasy.football.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Getter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class AppConfiguration {

    private Jwt jwt = new Jwt();

    private Game game = new Game();

    private ApiFootball apiFootball = new ApiFootball();

    @Getter
    @Setter
    public static class Game {

        private Double startCash = 100.0;
        private Double defaultCost = 0.0;
    }

    @Getter
    @Setter
    public static class Jwt {

        @NotBlank
        private String base64Secret;

        private long accessTokenValidity;

        private long refreshTokenValidity;

    }

    @Getter
    @Setter
    public static class ApiFootball {

        @NotBlank
        private String baseUrl;

        @NotBlank
        private String host;

        @NotBlank
        private String key;

        private boolean enable = false;
    }

}