package dev.bstk.okkurlcurtospring.okkurlspring.domain.data;

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

    @NaturalId
    @Column(name = "TOKEN", unique = true)
    private String token;

    @URL
    @Column(name = "URL_ORIGINAL")
    private String urlOriginal;

    @URL
    @Column(name = "URL_ENCURTADA")
    private String urlEncurtada;

    @Column(name = "URL_ENCURTADA_QR_CODE")
    private String urlEncurtadaQRCode;
}
