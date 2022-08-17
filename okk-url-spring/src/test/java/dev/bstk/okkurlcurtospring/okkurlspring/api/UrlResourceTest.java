package dev.bstk.okkurlcurtospring.okkurlspring.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    @DisplayName("[ POST ] - Deve retotnar uma url encurtada valida")
    void deveRetotnarUmaUrlValida() throws Exception {
        final String url = "https://www.google.com.br/hahhskka/skksjja-oosj";

        mockMvc.perform(
                post(ENDPOINT_ENCURTAR_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        MAPPER.writeValueAsString(
                            mockRequest(url)
                        )
                    )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.url_original").isString())
            .andExpect(jsonPath("$.url_original").isNotEmpty())
            .andExpect(jsonPath("$.url_original").value(url))
            .andExpect(jsonPath("$.url_encurtada").isNotEmpty())
            .andExpect(jsonPath("$.url_encurtada").isNotEmpty());
    }

    @Test
    @DisplayName("[ POST ] - Deve retotnar erro de validacao dado uma url invalida")
    void deveRetotnarUmErroDeValidacaoDadoUmaUrlInvalida() throws Exception {
        mockMvc.perform(
                post(ENDPOINT_ENCURTAR_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        MAPPER.writeValueAsString(
                            mockRequest("www.google.")
                        )
                    )
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.url_original").doesNotExist())
            .andExpect(jsonPath("$.url_encurtada").doesNotExist());
    }

    @Test
    @DisplayName("[ POST ] - Deve retotnar erro de validacao dado uma url com tamanho minimo")
    void deveRetotnarUmErroDeValidacaoDadoUmaUrlComTamanhoMinimo() throws Exception {
        mockMvc.perform(
                post(ENDPOINT_ENCURTAR_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        MAPPER.writeValueAsString(
                            mockRequest("https://www.google.com.br")
                        )
                    )
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.url_original").doesNotExist())
            .andExpect(jsonPath("$.url_encurtada").doesNotExist());
    }

    @Test
    @DisplayName("[ GET ] - Deve redireceionar de uma url curta para url longa")
    void deveRedireceionarDeUmaUrlCurtaParaUrlLonga() throws Exception {
        mockMvc.perform(
                get(ENDPOINT_REDIRECIONAR_URL, "asLkjk"))
            .andExpect(status().isMovedPermanently())
            .andExpect(redirectedUrl("https://www.uol.com.br"));
    }

    private UrlRequest mockRequest(final String url) {
        final UrlRequest request = new UrlRequest();
        request.setUrl(url);

        return request;
    }
}
