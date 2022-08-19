package dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder;

public interface Encoder<T> {

    String encode(final T number);
}
