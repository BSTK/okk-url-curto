package dev.bstk.okkurlcurtospring.okkurlspring.domain;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.Url;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.UrlRepository;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder.Base62;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder.QrCode;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.GerenciadorCache;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.handlerexception.exception.UrlTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@Service
@Validated
@RequiredArgsConstructor
public class UrlService {

    private static final String STRING_FORMAT_URL = "%s/%s";

    @Value("${okk-url-path}")
    private String okkUrl;

    private final QrCode qrCode;
    private final GerenciadorCache cache;
    private final UrlRepository repository;


    public Url encurtar(@Valid final UrlRequest request) {
        final var urlCache = (Url) cache.get(request.hashCode());
        if (Objects.nonNull(urlCache)) {
            return urlCache;
        }

        final var urlSalva = repository.save(new Url());
        final var urlToken = Base62.encode(urlSalva.getId());
        final var urlEncurtada = String.format(STRING_FORMAT_URL, okkUrl, urlToken);
        final var urlEncurtadaQRCode = qrCode.criarQrCode(urlEncurtada);

        urlSalva.setToken(urlToken);
        urlSalva.setUrlOriginal(request.getUrl());
        urlSalva.setUrlEncurtada(urlEncurtada);
        urlSalva.setUrlEncurtadaQRCode(urlEncurtadaQRCode);

        cache.put(urlToken, urlSalva);
        cache.put(request.hashCode(), urlSalva);

        return urlSalva;
    }

    public URI redirecionar(final String urlToken) {
        final var urlCache = (Url) cache.get(urlToken);
        if (Objects.nonNull(urlCache)) {
            return URI.create(urlCache.getUrlOriginal());
        }

        final var urlOriginal = repository.urlOriginal(urlToken);
        if (urlOriginal.isEmpty()) {
            throw new UrlTokenException(String.format("Não existe url curta para token: [ %s ]", urlToken));
        }

        return URI.create(urlOriginal.get());
    }
}
