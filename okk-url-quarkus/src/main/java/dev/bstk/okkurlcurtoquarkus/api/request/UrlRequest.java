package dev.bstk.okkurlcurtoquarkus.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlRequest implements Serializable {

    private static final int TAMANHO_MINIMO_DE_CARACTERES = 30;

    @URL
    @NotNull
    @JsonProperty("url")
    @Size(min = TAMANHO_MINIMO_DE_CARACTERES)
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        UrlRequest request = (UrlRequest) o;
        return Objects.equals(url, request.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
