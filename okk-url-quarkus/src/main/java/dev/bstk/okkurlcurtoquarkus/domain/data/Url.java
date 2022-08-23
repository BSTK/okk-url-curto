package dev.bstk.okkurlcurtoquarkus.domain.data;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TB_URL")
public class Url implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    @Column(name = "TOKEN", unique = true)
    private String token;

    @URL
    @Column(name = "URL_ORIGINAL")
    private String urlOriginal;

    @Column(name = "URL_ORIGINAL_QR_CODE", columnDefinition = "TEXT")
    private String urlOriginalQRCode;

    @URL
    @Column(name = "URL_ENCURTADA")
    private String urlEncurtada;
}
