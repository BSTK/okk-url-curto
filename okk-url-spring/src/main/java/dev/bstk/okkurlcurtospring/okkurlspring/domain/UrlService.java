package dev.bstk.okkurlcurtospring.okkurlspring.domain;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.Url;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.UrlRepository;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder.Encoder;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UrlService {

    @Value("${okk-url-path}")
    private String okkUrl;

    @Qualifier("EncoderBase62")
    private final Encoder base62;

    @Qualifier("EncoderQrCode")
    private final Encoder urlQrCode;

    private final Cache cache;
    private final UrlRepository repository;


    public Url encurtar(final UrlRequest request) {
        final var urlSalva = repository.save(new Url());
        final var urlToken = base62.encode(urlSalva.getId());
        final var urlEncurtada = String.format("%s/%s", okkUrl, urlToken);
        final var urlEncurtadaQRCode = urlQrCode.encode(urlEncurtada);

        urlSalva.setToken(urlToken);
        urlSalva.setUrlOriginal(request.getUrl());
        urlSalva.setUrlEncurtada(urlEncurtada);
        urlSalva.setUrlEncurtadaQRCode(urlEncurtadaQRCode);

        cache.put(urlToken, urlSalva);

        return urlSalva;
    }

    public URI redirecionar(final String urlToken) {
        final var urlCache = (Url) cache.get(urlToken);
        final var urlEncurtada = Objects.nonNull(urlCache)
            ? urlCache.getUrlOriginal()
            : repository.urlOriginal(urlToken);

        return URI.create(urlEncurtada);
    }
}
