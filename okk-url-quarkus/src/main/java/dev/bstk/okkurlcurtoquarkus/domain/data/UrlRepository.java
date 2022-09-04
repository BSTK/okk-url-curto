package dev.bstk.okkurlcurtoquarkus.domain.data;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NamedQuery;
import java.util.Optional;

@ApplicationScoped
@NamedQuery(name = "", query = "")
public class UrlRepository implements PanacheRepository<Url> {

    public Optional<String> urlOriginal(final String token) {
        return find("token", token).firstResultOptional().map(Url::getUrlOriginal);
    }

    public Optional<Url> url(final String urlOriginal) {
        return find("lower(trim(urlOriginal))", urlOriginal).firstResultOptional();
    }
}
