package dev.bstk.okkurlcurtospring.okkurlspring.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlResponse implements Serializable {

    @URL
    @NotNull
    @JsonProperty("url_original")
    private final String urlOriginal;

    @URL
    @NotNull
    @JsonProperty("url_encurtada")
    private final String urlEncurtada;

    @URL
    @NotNull
    @JsonProperty("url_qr_code")
    private final String urlQRCode;
}
