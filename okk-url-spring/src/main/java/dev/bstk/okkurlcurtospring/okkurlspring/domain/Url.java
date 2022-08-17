package dev.bstk.okkurlcurtospring.okkurlspring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Url implements Serializable {

    @URL
    @NotNull
    private final String urlOriginal;

    @URL
    @NotNull
    private final String urlEncurtada;
}
