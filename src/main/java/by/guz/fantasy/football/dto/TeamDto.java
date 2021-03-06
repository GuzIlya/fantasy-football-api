package by.guz.fantasy.football.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public enum  TeamDto {;
    private interface Id { @Positive Long getId(); }
    private interface ExternalId { @Positive Long getExternalId(); }
    private interface Name { @NotNull String getName(); }
    private interface Country { @NotNull String getCountry(); }
    private interface Founded { @NotNull String getFounded(); }
    private interface National { @NotNull String getNational(); }
    private interface Logo { String getLogo(); }

    public enum Response {;
        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Default implements Id, Name, Country, Founded, Logo {
            Long id;
            String name;
            String country;
            String founded;
            String logo;
        }
    }

    public enum External {;

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DefaultList {
            List<DefaultCover> response;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DefaultCover {
            Default team;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Default implements Id, Name, Country, Founded, National, Logo {
            Long id;
            String name;
            String country;
            String founded;
            String national;
            String logo;
        }

    }

}
