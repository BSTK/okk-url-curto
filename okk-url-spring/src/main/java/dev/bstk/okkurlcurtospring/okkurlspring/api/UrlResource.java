package dev.bstk.okkurlcurtospring.okkurlspring.api;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import dev.bstk.okkurlcurtospring.okkurlspring.api.response.UrlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/url")
public class UrlResource {

    @Value("${okk-url-path}")
    private String okkUrl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UrlResponse> encurtar(@RequestBody @Valid final UrlRequest request) {
        final UrlResponse response = new UrlResponse(
            request.getUrl(),
            String.format("%s/%s", okkUrl, UUID.randomUUID().toString().split("-")[0])
        );

        return ResponseEntity.ok(response);
    }


}
