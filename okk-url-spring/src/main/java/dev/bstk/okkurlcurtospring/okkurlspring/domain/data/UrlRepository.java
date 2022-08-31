package dev.bstk.okkurlcurtospring.okkurlspring.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query("SELECT u.urlOriginal FROM Url u WHERE u.token = :token")
    Optional<String> urlOriginal(@Param("token") final String token);

    @Query("SELECT u FROM Url u WHERE lower(u.urlOriginal) = lower(trim(:urlOriginal)) ")
    Optional<Url> url(@Param("urlOriginal") final String urlOriginal);
}
