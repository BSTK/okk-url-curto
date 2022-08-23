package dev.bstk.okkurlcurtoquarkus.domain.data;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UrlRepository implements PanacheRepository<Url> {

    public Optional<String> urlOriginal(final String token) {
        return find("token", token).firstResultOptional().map(Url::getUrlOriginal);
    }
}
