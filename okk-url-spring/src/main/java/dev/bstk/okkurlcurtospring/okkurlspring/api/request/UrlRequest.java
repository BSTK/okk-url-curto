package dev.bstk.okkurlcurtospring.okkurlspring.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlRequest implements Serializable {

    @URL
    @NotNull
    @JsonProperty("url")
    private String url;
}
