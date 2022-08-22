package dev.bstk.okkurlcurtospring.okkurlspring.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.Url;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.UrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlResource.class)
@ExtendWith(SpringExtension.class)
class UrlResourceTest {

    private static final String ENDPOINT_ENCURTAR_URL = "/url";
    private static final String ENDPOINT_REDIRECIONAR_URL = "/{url_id}";

    private static final ObjectMapper MAPPER = new ObjectMapper();


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    @Test
    @DisplayName("[ POST ] - Deve retotnar uma url encurtada valida")
    void deveRetotnarUmaUrlValida() throws Exception {
        final var urlOriginal = "https://www.google.com.br/hahhskka/skksjja-oosj";
        final var request = mockRequest(urlOriginal);
        mockWhen(urlOriginal);

        mockMvc.perform(
                post(ENDPOINT_ENCURTAR_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(MAPPER.writeValueAsString(request))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.url_original").isString())
            .andExpect(jsonPath("$.url_original").isNotEmpty())
            .andExpect(jsonPath("$.url_original").value(urlOriginal))
            .andExpect(jsonPath("$.url_encurtada").isNotEmpty())
            .andExpect(jsonPath("$.url_encurtada").isNotEmpty());
    }

    @Test
    @DisplayName("[ POST ] - Deve retotnar erro de validacao dado uma url invalida")
    void deveRetotnarUmErroDeValidacaoDadoUmaUrlInvalida() throws Exception {
        final var urlOriginal = "www.google.";
        final var request = mockRequest(urlOriginal);
        mockWhen(urlOriginal);

        mockMvc.perform(
                post(ENDPOINT_ENCURTAR_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        MAPPER.writeValueAsString(request)
                    )
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.url_original").doesNotExist())
            .andExpect(jsonPath("$.url_encurtada").doesNotExist());
    }

    @Test
    @DisplayName("[ POST ] - Deve retotnar erro de validacao dado uma url com tamanho minimo")
    void deveRetotnarUmErroDeValidacaoDadoUmaUrlComTamanhoMinimo() throws Exception {
        final var urlOriginal = "https://www.google.com.br";
        final var request = mockRequest(urlOriginal);
        mockWhen(urlOriginal);

        mockMvc.perform(
                post(ENDPOINT_ENCURTAR_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        MAPPER.writeValueAsString(request)
                    )
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.url_original").doesNotExist())
            .andExpect(jsonPath("$.url_encurtada").doesNotExist());
    }

    @Test
    @DisplayName("[ GET ] - Deve redireceionar de uma url curta para url longa")
    void deveRedireceionarDeUmaUrlCurtaParaUrlLonga() throws Exception {
        when(urlService.redirecionar("mockId"))
            .thenReturn(URI.create("https://www.mock-url.com.br"));

        mockMvc.perform(
                get(ENDPOINT_REDIRECIONAR_URL, "mockId"))
            .andExpect(status().isMovedPermanently())
            .andExpect(redirectedUrl("https://www.mock-url.com.br"));
    }

    private void mockWhen(final String urlOriginal) {
        final var url = new Url();
        url.setToken("okL");
        url.setUrlOriginal(urlOriginal);
        url.setUrlOriginalQRCode("QR_CODE");
        url.setUrlEncurtada("https://www.mock-url/okL");

        final var request = mockRequest(urlOriginal);
        when(urlService.encurtar(request)).thenReturn(url);
    }

    private UrlRequest mockRequest(final String url) {
        final UrlRequest request = new UrlRequest();
        request.setUrl(url);

        return request;
    }
}
