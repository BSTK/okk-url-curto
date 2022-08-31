package dev.bstk.okkurlcurtospring.okkurlspring.api;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import dev.bstk.okkurlcurtospring.okkurlspring.api.response.UrlResponse;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.UrlService;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.GerenciadorCache;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UrlResource {

    private final UrlService urlService;
    private final GerenciadorCache cache;


    @PostMapping("/url")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UrlResponse> encurtar(@RequestBody @Valid final UrlRequest request) {
        final var urlEncurtadada = urlService.encurtar(request);
        final UrlResponse urlResponse = new UrlResponse(
            urlEncurtadada.getUrlOriginal(),
            urlEncurtadada.getUrlEncurtada(),
            urlEncurtadada.getUrlOriginalQRCode()
        );

        return ResponseEntity.ok(urlResponse);
    }

    @GetMapping("/{url_token}")
    public ResponseEntity<Void> redirecionar(@PathVariable("url_token") final String urlToken) {
        final var urlRedirecionar = urlService.redirecionar(urlToken);
        return ResponseEntity
            .status(HttpStatus.FOUND)
            /// .status(HttpStatus.MOVED_PERMANENTLY)
            .location(urlRedirecionar)
            .build();
    }
}
