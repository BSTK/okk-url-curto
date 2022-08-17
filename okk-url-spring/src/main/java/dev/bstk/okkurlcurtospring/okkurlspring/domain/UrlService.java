package dev.bstk.okkurlcurtospring.okkurlspring.domain;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
public class UrlService {

    @Value("${okk-url-path}")
    private String okkUrl;


    /// TODO: IMPLEMENTAR
    public Url encurtar(final UrlRequest request) {
        final String urlOriginal = request.getUrl();
        final String urlEncurtada = String.format("%s/%s", okkUrl, UUID.randomUUID().toString().split("-")[0]);

        return new Url(urlOriginal, urlEncurtada);
    }

    /// TODO: IMPLEMENTAR
    public URI redirecionar(final String urlId) {
        return URI.create("https://www.uol.com.br");
    }
}
