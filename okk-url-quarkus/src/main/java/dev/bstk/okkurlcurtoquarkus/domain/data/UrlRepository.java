package dev.bstk.okkurlcurtoquarkus.domain.data;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UrlRepository implements PanacheRepository<Url> {

    // @Query("SELECT u.urlOriginal FROM Url u WHERE u.token = :token")
    public Optional<String> urlOriginal(final String token) {
        return Optional.empty();
    }
}

