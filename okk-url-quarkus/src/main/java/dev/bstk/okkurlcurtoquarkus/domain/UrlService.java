package dev.bstk.okkurlcurtoquarkus.domain;

import dev.bstk.okkurlcurtoquarkus.api.request.UrlRequest;
import dev.bstk.okkurlcurtoquarkus.domain.data.Url;
import dev.bstk.okkurlcurtoquarkus.domain.data.UrlRepository;
import dev.bstk.okkurlcurtoquarkus.domain.encoder.Base62;
import dev.bstk.okkurlcurtoquarkus.domain.encoder.QrCode;
import dev.bstk.okkurlcurtoquarkus.infra.cache.GerenciadorCache;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.UrlTokenException;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@ApplicationScoped
@RequiredArgsConstructor
public class UrlService {

    private static final String STRING_FORMAT_URL = "%s/%s";

    @ConfigProperty(name = "okk-url-path")
    protected String okkUrl;

    @Inject
    protected QrCode qrCode;

    @Inject
    protected GerenciadorCache cache;

    @Inject
    protected UrlRepository repository;


    public Url encurtar(@Valid final UrlRequest request) {
        final var urlCache = (Url) cache.get(request.hashCode());
        if (Objects.nonNull(urlCache)) {
            return urlCache;
        }

        final var urlSalva = new Url();
        repository.persist(urlSalva);

        final var urlToken = Base62.encode(urlSalva.getId());
        final var urlEncurtada = String.format(STRING_FORMAT_URL, okkUrl, urlToken);
        final var urlOriginalQRCode = qrCode.criarQrCode(request.getUrl());

        urlSalva.setToken(urlToken);
        urlSalva.setUrlOriginal(request.getUrl());
        urlSalva.setUrlEncurtada(urlEncurtada);
        urlSalva.setUrlOriginalQRCode(urlOriginalQRCode);

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
